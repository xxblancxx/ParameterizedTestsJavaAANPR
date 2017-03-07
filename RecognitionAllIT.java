/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.javaanpr.test;

/**
 *
 * @author martin
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;
import javax.xml.parsers.ParserConfigurationException;

import net.sf.javaanpr.imageanalysis.CarSnapshot;
import net.sf.javaanpr.intelligence.Intelligence;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.xml.sax.SAXException;

@RunWith(Parameterized.class)
public class RecognitionAllIT {

    private File image;
    private String expectedLicencePlate;

    // Constructor.
    // The JUnit test runner will instantiate this class once for every
    //element in the Collection returned by the method annotated with
    // @Parameters.
    public RecognitionAllIT(File image, String expectedLicencePlate) {
        this.image = image;
        this.expectedLicencePlate = expectedLicencePlate;
    }

    @Parameters
    public static Collection<Object[]> generateData() throws FileNotFoundException, IOException {
        String imagePath = "src/test/resources/snapshots";
        String resultsPath = "src/test/resources/results.properties";
        InputStream resultsStream = new FileInputStream(new File(resultsPath));

        Properties properties = new Properties();
        properties.load(resultsStream);
        resultsStream.close();

        File snapshotDir = new File(imagePath);
        File[] snapshots = snapshotDir.listFiles();
        Collection<Object[]> imageData = new ArrayList<>();
        for (File snap : snapshots) {
            String name = snap.getName();
            String plateExpected = properties.getProperty(name);
            imageData.add(new Object[]{snap, plateExpected});
        }
        return imageData;
    }

    @Test
    public void intelligenceSingleTest() throws IOException, ParserConfigurationException, SAXException {

        CarSnapshot carSnap = new CarSnapshot(new FileInputStream(image));
        assertNotNull("carSnap is null", carSnap);
        assertNotNull("carSnap.image is null", carSnap.getImage());
        Intelligence intel = new Intelligence();
        assertNotNull(intel);
        String spz = intel.recognize(carSnap);
        assertNotNull("The licence plate is null - are you sure the image has the correct color space?", spz);
        assertEquals(expectedLicencePlate, spz);
        carSnap.close();
    }

}
