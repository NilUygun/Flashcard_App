/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.flashcards;

/**
 *
 * @author uygun
 */
import java.awt.Font;
import javax.swing.UIManager;

public class FontConstants {
    public static final Font labelFont = new Font(UIManager.getDefaults().getFont("TabbedPane.font").getFamily(),
            Font.PLAIN, 14);
    public static final Font textAreaFont = new Font(UIManager.getDefaults().getFont("TabbedPane.font").getFamily(),
            Font.PLAIN, 16);
}
