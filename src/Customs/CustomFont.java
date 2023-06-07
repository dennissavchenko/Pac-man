package Customs;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CustomFont {
    public static String getCustomFont(String fileName) {
        try {
            InputStream is = new FileInputStream("./Fonts/" + fileName);
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, is);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            return customFont.getFontName();
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
        return "Not Found";
    }
}
