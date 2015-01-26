/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package P7GUI;

import javax.swing.JFrame;

/**
 *
 * @author ARISTOCRAT
 */
public class MF {
    
    public static void main(String[] args){
        
        JFrame frm = new JFrame();
            frm.setSize(400,400);
            frm.setTitle("Calculator");
            frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frm.setContentPane(new Login());
            frm.setVisible(true);
    }
}
