package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * Created by Jonneh on 06/07/2016.
 *
 * https://www.reddit.com/r/gamedev/comments/3m1fvy/libgdx_and_japan_chinese_language/
 *
 * http://welina.holy.jp/font/tegaki/chif/
 */
public class JapaneseGenerator {
    public String FONT_CHARS;
    public String FONT_PATH;
    FreeTypeFontGenerator generator;
    FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    public static BitmapFont jFont;



    JapaneseGenerator(){
        // this should be made to prepare font from a SET made from assets. local variable
        // OR generator can be reused every time japanese is printed on screen (not ideal i don't think)

        // move this out of constructor into a reusable method.  with parameter dicating the FONT_CHARS (as discussed above)
        // or should this just be done in Assets. no need for JapaneseGenerator class really...
        String FONT_CHARS = "そそれ";

        final String FONT_PATH = "chichiya.ttf";
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(FONT_PATH));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.characters = FONT_CHARS;
        parameter.size = 15;
        parameter.color = Color.WHITE;
        jFont = generator.generateFont(parameter);
        generator.dispose();
    }
}
