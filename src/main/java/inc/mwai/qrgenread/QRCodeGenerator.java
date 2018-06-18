package inc.mwai.qrgenread;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.web.client.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.json.JSONArray;

import org.json.JSONObject;
import org.json.XML;

public class QRCodeGenerator {

    private static String QR_CODE_IMAGE_PATH = "./QRCODES/";

    private static void generateQRCodeImage(String text, int width, int height, String filePath)
            throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }

    private static String getGuests() {
        final String uri = "http://guestqrweb-wahimwai260204.codeanyapp.com/api/guests";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        return result;
    }

    private byte[] getQRCodeImageByteArray(String text, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray();
        return pngData;
    }

    private static void generateDummyData() {
        try {
        String id = "21976606";
        String name =  "Robert Njuguna";
        String company_name =  "Kass Media";
        String designation =  "Creative";
        String valid_thru ="6/2023";

        QR_CODE_IMAGE_PATH = QR_CODE_IMAGE_PATH + name + ".png";
        QR_CODE_IMAGE_PATH = QR_CODE_IMAGE_PATH.replace(' ', '_');
        String employee = "<employee><id>" + id + "</id><name>" + name + "</name><company_name>" + company_name + "</company_name><designation>" + designation + "</designation><valid_thru>" + valid_thru + "</valid_thru></employee>";

        JSONObject employeeJSONObject = XML.toJSONObject(employee);
        String employeeJSON = employeeJSONObject.toString();
        System.out.println(employee);

        generateQRCodeImage(employeeJSON, 100, 100, QR_CODE_IMAGE_PATH);
        QR_CODE_IMAGE_PATH = "./QRCODES/";
         } catch (WriterException e) {
            System.out.println("Could not generate QR Code, WriterException :: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Could not generate QR Code, IOException :: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        generateDummyData();
        /*try {
            JSONObject object = new JSONObject(getGuests());//getGuests() Returns JSON String
            JSONArray jsonarr = object.getJSONArray("guests");
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
            for (int i = 0; i < jsonarr.length(); i++) {
                JSONObject jsonobj = jsonarr.getJSONObject(i);
                String id = jsonobj.getString("id");
                String first_name = jsonobj.getString("first_name");
                String middle_name = jsonobj.getString("middle_name");
                String last_name = jsonobj.getString("last_name");
                String organization = jsonobj.getString("organization");
                String category = jsonobj.getString("category");

                QR_CODE_IMAGE_PATH = QR_CODE_IMAGE_PATH + first_name + "_" + last_name + ".png";
                QR_CODE_IMAGE_PATH = QR_CODE_IMAGE_PATH.replace(' ', '_');
                String guest = "<guest><id>" + id + "</id><first_name>" + first_name + "</first_name><middle_name>" + middle_name + "</middle_name><last_name>" + last_name + "</last_name><organization>" + organization + "</organization><category>" + category + "</category></guest>";

                JSONObject guestJSONObject = XML.toJSONObject(guest);
                String guestJSON = guestJSONObject.toString();
                System.out.println(guestJSON);

                generateQRCodeImage(guestJSON, 100, 100, QR_CODE_IMAGE_PATH);
                QR_CODE_IMAGE_PATH = "./QRCODES/";
            }

        } catch (WriterException e) {
            System.out.println("Could not generate QR Code, WriterException :: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Could not generate QR Code, IOException :: " + e.getMessage());
        }*/
    }
}
