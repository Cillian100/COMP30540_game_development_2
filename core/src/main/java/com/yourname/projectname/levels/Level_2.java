package com.yourname.projectname.levels;

import com.yourname.projectname.entities.Box;
import com.yourname.projectname.entities.Skull;
import com.yourname.projectname.entities.BulletEntity;
import com.yourname.projectname.entities.EndOfLevel;
import java.util.Vector;

public class Level_2 extends Level_Master{
    Box groundBox, groundBox_2;
    BulletEntity bullet;
    int currentFrames=0;
    int counter=0;

    @Override
    public void create() {
        super.create();
        groundBox = new Box(0f, 0f, 25f, 10f, 0.5f, 60f);
        skull = new Skull(0f, 0.5f, 35f, 10f, 10f, 10f);
        bulletVec.add(new BulletEntity(0f, 0.5f, 35f, 1f, 1f, 1f));
        endOfLevel = new EndOfLevel(0f, 0f, 55f, 5f, 5f, 2f);
        
        skullArray.add(skull);
        instance.add(endOfLevel.getModel());
        instance.add(groundBox.getModel());
        instance.add(skull.getModel());
        instance.add(bulletVec.get(0).getModel());
        groundArray.add(groundBox);

        hasGround=true;
        hasCoins=false;
        hasEndOfLevel=true;
        hasPowerUp=false;
        hasEnemy=false;
        hasBullets=true;
        hasSkull=true;
    }

    public void childRender(float delta, int frames){
        if(hasSkull==true){
            skull.movementFunction(-5f, 5f, delta, 20f);
        }

        if(skull.getHealth()<=0){
            hasSkull=false;
            instance.remove(skull.getModel());
        }
        
        for(int a=0;a<bulletVec.size();a++){
            bulletVec.get(a).movementFunction(delta, (20f*delta) - getPlayerMovement());
        }
        if(currentFrames+50<frames){
            createBullets();
            currentFrames=frames;
        }
    }

    public void childTextRender(){
        if(bossFight==true){
            font.draw(spriteBatch, "Boss Health: " + (int)skull.getHealth() + score, 100, 250);
        }
    }

    public void createBullets(){
        counter=counter%10;
        if(bulletVec.size()<10){
            bullet = new BulletEntity(skull.getX(), skull.getY(), skull.getZ(), 1f, 1f, 1f);
            bulletVec.add(bullet);
            instance.add(bulletVec.get(bulletVec.size() - 1).getModel());
        }else{
            instance.remove(bulletVec.get(counter));
            bulletVec.set(counter, new BulletEntity(skull.getX(), skull.getY(), skull.getZ(), 1f, 1f, 1f));
            instance.add(bulletVec.get(counter).getModel());
            counter++;
        }
    }
}
