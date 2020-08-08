package GameState;

import Entity.*;
import Entity.Enemies.*;
import Main.GamePanel;
import TileMap.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Level1State extends GameState {
	
	private TileMap tileMap;
        private Background bg;
        private Player player;
        private HUD hud;
        Point[] points1,points;
        private int lastX,lastY;
        private Boolean nextLevel;
        private int currentlevel=1;
        Level2State level2state;
        
        private ArrayList<Enemy>enemies;
	 private ArrayList<Explosion>explosions;
	public Level1State(GameStateManager gsm) {
		this.gsm = gsm;
		init();
	}
	
	public void init() {
		
		tileMap = new TileMap(30);
                level2state=new Level2State(gsm);
		tileMap.loadTiles("/Resources/grasstileset.gif");
		tileMap.loadMap("/Resources/level1-1.map");
		tileMap.setPosition(0, 0);
                tileMap.setTween(0.07);
                bg=new Background("/Resources/grassbg-3.jpg",0.1);
                player=new Player(tileMap);
                player.setPosition(100,100);
                
                populateEnemies();
               
               
               explosions=new ArrayList<Explosion>();
               
               hud= new  HUD(player);
               
        
		
	}
        private void populateEnemies()
        {
               enemies=new ArrayList<Enemy>();
               Slugger s;
               points= new Point[]
               {
                   new Point(200,200),
                 new Point(860,200),
                   new Point(1525,200),
                   new Point(1680,200),
               new Point(1800,200)
               
               };
              points1= new Point[]
               {
                   new Point(100,200),
                 new Point(450,110),
                   new Point(925,170),
                   new Point(1900,110),
                   new Point(3050,200)
          
               
               };
             for(int i=0;i<points.length;i++)
             {
                 s= new Slugger(tileMap);
               s.setPosition(points[i].x, points[i].y);
               enemies.add(s);
             }
        }
	
	
	public void update() {
           if(hud.isThisLevelDone())
                {
                   gsm.setState(GameStateManager.LEVEL2STATE);
                   return;
                   
                   
                }
            if(player.isDead()==true)
            {
                init();
                
            }
        player.update();
        tileMap.setPosition(GamePanel.WIDTH/2-player.getx(), GamePanel.HEIGHT/2-player.gety());
        bg.setPosition(tileMap.getx(), tileMap.gety());
           System.out.println(""+player.getx()+" "+player.gety());
           
            for(int i=1;i<points1.length;i++)
            {
                if(points1[i].x>=player.getx())
                {
                    lastX=points1[i-1].x;
                    lastY=points1[i-1].y;
                    break;
                }
            }
        player.checkAttack(enemies);
        if(player.dropPlayer((int)player.getx(),(int)player.gety()))
        {
            player.setPosition(lastX, lastY);
          
        }
        for(int i=0;i<enemies.size();i++)
        {
            Enemy e=enemies.get(i);
            e.update();
            if(e.isDead())
            {
                enemies.remove(i);
                i--;
                explosions.add(new Explosion((int)e.getx(),(int) e.gety()));
            }
        }
         for(int i=0;i<explosions.size();i++)
                {
                    
                    explosions.get(i).update();
                if(explosions.get(i).shouldRemove())
                {
                    explosions.remove(i);
                }
                }
        
        }
	
	public void draw(Graphics2D g) {
            
		
		// clear screen
		bg.draw(g);
		
		// draw tilemap
		tileMap.draw(g);
                player.draw(g);
                
                for(int i=0;i<enemies.size();i++)
                {
                    enemies.get(i).draw(g);
                }
                
                for(int i=0;i<explosions.size();i++)
                {
                    explosions.get(i).setMapPosition((int)tileMap.getx(), (int)tileMap.gety());
                    explosions.get(i).draw(g);
                }
                
                hud.draw(g,1,(int)player.getx());
               if(hud.isThisLevelDone())
                {
                   return;
                   
                   
                }
		
	}
	
	public void keyPressed(int k) {
        if(k==KeyEvent.VK_LEFT)
        {
            player.setLeft(true);
        }
        if(k==KeyEvent.VK_RIGHT) player.setRight(true);
        if(k==KeyEvent.VK_UP) player.setUp(true);
        if(k==KeyEvent.VK_DOWN) player.setDown(true);
        if(k==KeyEvent.VK_W) player.setJumping(true);
        if(k==KeyEvent.VK_E) player.setGliding(true);
        if(k==KeyEvent.VK_R) player.setScratching();
        if(k==KeyEvent.VK_F) player.setFiring();
        }
	
	public void keyReleased(int k) {
        if(k==KeyEvent.VK_LEFT)
        {
            player.setLeft(false);
        }
        if(k==KeyEvent.VK_RIGHT) player.setRight(false);
        if(k==KeyEvent.VK_UP) player.setUp(false);
        if(k==KeyEvent.VK_DOWN) player.setDown(false);
        if(k==KeyEvent.VK_W) player.setJumping(false);
        if(k==KeyEvent.VK_E) player.setGliding(false);
        
        }
	
}












