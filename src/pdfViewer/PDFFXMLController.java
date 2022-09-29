package pdfViewer;

import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import org.icepdf.ri.util.FontPropertiesManager;
import org.icepdf.ri.util.PropertiesManager;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PDFFXMLController implements Initializable {

    public static String pdfPath;
    private SwingController swingController;
    private JComponent viewerPanel;
    @FXML
    private BorderPane borderPane;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String pdfPathLoad ;
        //pdfPath = "src\\PdfViewer\\pdfviewer.pdf";
        try {
            pdfPathLoad = loadPDF("");
            createViewer(borderPane);
            openDocument(pdfPath);
        } catch (IOException |NullPointerException ex) {
            Logger.getLogger(PDFFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("error location");
        }
    }

    private void createViewer(BorderPane borderPane) {
        try {
            SwingUtilities.invokeAndWait(() -> {
                swingController = new SwingController();
                swingController.setIsEmbeddedComponent(true);
                PropertiesManager properties = new PropertiesManager(System.getProperties(),
                        ResourceBundle.getBundle(PropertiesManager.DEFAULT_MESSAGE_BUNDLE));
                properties.set(PropertiesManager.PROPERTY_SHOW_TOOLBAR_FIT, "false");
                properties.set(PropertiesManager.PROPERTY_SHOW_TOOLBAR_ROTATE, "false");
                properties.set(PropertiesManager.PROPERTY_SHOW_TOOLBAR_TOOL, "true");
                properties.set(PropertiesManager.PROPERTY_DEFAULT_ZOOM_LEVEL, "2");
                properties.setBoolean(PropertiesManager.PROPERTY_SHOW_STATUSBAR_VIEWMODE, Boolean.FALSE);
                properties.set(PropertiesManager.PROPERTY_SHOW_TOOLBAR_PAGENAV, "true");
                ResourceBundle messageBundle = ResourceBundle.getBundle(PropertiesManager.DEFAULT_MESSAGE_BUNDLE);
                new FontPropertiesManager(properties, System.getProperties(), messageBundle);
                swingController.getDocumentViewController().setAnnotationCallback(
                        new org.icepdf.ri.common.MyAnnotationCallback(swingController.getDocumentViewController()));
                SwingViewBuilder factory = new SwingViewBuilder(swingController, properties);
                viewerPanel = factory.buildViewerPanel();
                viewerPanel.revalidate();
                SwingNode swingNode = new SwingNode();
                swingNode.setContent(viewerPanel);
                borderPane.setCenter(swingNode);
                swingController.setToolBarVisible(false);
                swingController.setUtilityPaneVisible(false);
            });
        } catch (InterruptedException | InvocationTargetException e) {
        }
    }
    private void openDocument(String document) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                swingController.openDocument(document);
                viewerPanel.revalidate();
            }
        });
    }
    public String loadPDF(String address) throws IOException {
        System.out.println("In load PDf");
        if (!address.toLowerCase().endsWith("pdf")) {
            return null;
        }
        String fileName = address.substring(address.lastIndexOf("/") + 1,
                address.lastIndexOf("."));
        String suffix = address.substring(address.lastIndexOf("."),
                address.length());
        File temp = null;
        try {
            InputStream input = new URL(address).openStream();
            temp = File.createTempFile(fileName, suffix);
            temp.deleteOnExit();
            Files.copy(input, Paths.get(temp.toURI()),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (MalformedURLException ex) {
            Logger.getLogger(PDFFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        assert temp != null;
        return temp.getAbsolutePath();
    }

}
