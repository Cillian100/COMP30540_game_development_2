package com.yourname.projectname.levels;

import com.yourname.projectname.entities.Box;
import com.yourname.projectname.entities.Skull;
import com.yourname.projectname.entities.Bullet;
import java.util.Vector;

public class Level_2 extends Level_Master{
    Box groundBox, groundBox_2;
    Skull skull;
    Vector<Bullet> bulletVec = new Vector<Bullet>();
    Bullet bullet;
    int currentFrames=0;
    int counter=0;

    @Override
    public void create() {
        super.create();
        groundBox = new Box(0f, 0f, 250f, 10f, 0.5f, 500f);
        skull = new Skull(0f, 0.5f, 35f, 3f, 3f, 3f);
        bulletVec.add(new Bullet(0f, 0.5f, 35f, 1f, 1f, 1f));
        
        skullArray.add(skull);
        instance.add(groundBox.getModel());
        instance.add(skull.getModel());
        instance.add(bulletVec.get(0).getModel());
        groundArray.add(groundBox);

        hasGround=true;
        hasCoins=false;
        hasEndOfLevel=false;
        hasPowerUp=false;
        hasEnemy=false;
    }

    public void childRender(float delta, int frames){
        System.out.println(30f*delta - getPlayerMovement());
        skull.movementFunction(-5f, 5f, delta, 20f);

        for(int a=0;a<bulletVec.size();a++){
            bulletVec.get(a).movementFunction(delta, (20f*delta) - getPlayerMovement());
        }
        if(currentFrames+50<frames){
            createBullets();
            currentFrames=frames;
        }
    }

    public void createBullets(){
        counter=counter%10;
        if(bulletVec.size()<10){
            bullet = new Bullet(skull.getX(), skull.getY(), skull.getZ(), 1f, 1f, 1f);
            bulletVec.add(bullet);
            instance.add(bulletVec.lastElement().getModel());
        }else{
            instance.remove(bulletVec.get(counter));
            bulletVec.set(counter, new Bullet(skull.getX(), skull.getY(), skull.getZ(), 1f, 1f, 1f));
            instance.add(bulletVec.get(counter).getModel());
            counter++;
        }
    }
}
