/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 *
 * @author Foysal
 */
public class HUD {
    private Player player;
    private BufferedImage image;
    private Font font,font1,font2; 
    private long timer,timer1;
    private boolean complete,doneThisLevel;
    
    public HUD(Player p)
    {
        player=p;
        try{
            
        image=ImageIO.read(
                
                    getClass().getResourceAsStream("/Resources/hud.gif")
        );
        font=new Font("Arial",Font.PLAIN,14);
         font1=new Font("Arial",Font.PLAIN,40);
         font2=new Font("Arial",Font.PLAIN,20);
         timer=0;
         timer1=0;
        
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
                    
    }
    public void draw(Graphics2D g,int level,int xx)
    {
       g.drawImage(image, 0, 0, null);
       g.setFont(font);
       g.setColor(Color.RED);
      g.drawString(player.getHealth()+"/"+player.getMaxHealth(), 30, 15);
      g.drawString(player.getFire()+"/"+player.getMaxFire(), 30, 35);
     if(timer<=250)
     {
       timer++;
      g.setColor(Color.white);
      g.setFont(font1);
      g.drawString("LEVEL "+level, 100, 50);
     }
     if(xx>=3050 || complete)
     {
         timer1++;
               complete=true;
               g.setColor(Color.white);
                g.setFont(font2);
                g.drawString("LEVEL "+level+" COMPLETED", 40, 50);
                if(timer1>=250)
                {
                    doneThisLevel=true;
                }
     }
    }
    public boolean isThisLevelDone()
    {
        return doneThisLevel;
    }
    
    
}
