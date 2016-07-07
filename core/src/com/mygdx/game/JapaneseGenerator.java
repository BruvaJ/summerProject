package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Jonneh on 06/07/2016.
 *
 * https://www.reddit.com/r/gamedev/comments/3m1fvy/libgdx_and_japan_chinese_language/
 *
 * http://welina.holy.jp/font/tegaki/chif/
 */
public class JapaneseGenerator {
    private static String FONT_CHARS;
    public static String FONT_PATH;
    public static FreeTypeFontGenerator generator;
    public static FreeTypeFontGenerator.FreeTypeFontParameter parameter;



    JapaneseGenerator(){
        // this should be made to prepare font from a SET made from assets. local variable
        // OR generator can be reused every time japanese is printed on screen (not ideal i don't think)

        // move this out of constructor into a reusable method.  with parameter dicating the FONT_CHARS (as discussed above)
        // or should this just be done in Assets. no need for JapaneseGenerator class really...

    }

    public static BitmapFont generate(String[] s){
        // static class members should be used instead?
        FONT_CHARS = uniqueChars(s);

        final String FONT_PATH = "chichiya.ttf";
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(FONT_PATH));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.characters = FONT_CHARS;
        parameter.size = 15;
        parameter.color = Color.WHITE;
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();
        return font;
    }

    private static String uniqueChars(String[] sArray) {
        Set<Character> charArray = new HashSet<Character>();
        for (String s:sArray) {
            for(Character c : s.toCharArray()){
                charArray.add(c);
            }
        }
        return charArray.toString();
    }
}
