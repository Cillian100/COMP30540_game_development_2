package com.yourname.projectname.logic;

import java.util.ArrayList;
import java.util.Vector;
//import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.physics.bullet.collision.CollisionObjectWrapper;
import com.badlogic.gdx.physics.bullet.collision.btCollisionAlgorithm;
import com.badlogic.gdx.physics.bullet.collision.btCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btCollisionDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btDefaultCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btDispatcherInfo;
import com.badlogic.gdx.physics.bullet.collision.btManifoldResult;
import com.badlogic.gdx.utils.Array;
import com.yourname.projectname.entities.Box;
import com.yourname.projectname.entities.Enemy;
import com.yourname.projectname.entities.Player;
import com.yourname.projectname.entities.BulletEntity;
import com.yourname.projectname.entities.BulletEntityPlayer;
import com.yourname.projectname.entities.Skull;
import com.yourname.projectname.entities.Coin;
import com.badlogic.gdx.graphics.g3d.ModelInstance;


public class CollisionDetection {
    btDispatcher dispatcher;
    btCollisionConfiguration collisionConfig;

    public CollisionDetection(){
        collisionConfig = new btDefaultCollisionConfiguration();
        dispatcher = new btCollisionDispatcher(collisionConfig);
    }

    public boolean checkCollision(btCollisionObject obj0, btCollisionObject obj1){
        CollisionObjectWrapper co0 = new CollisionObjectWrapper(obj0);
        CollisionObjectWrapper co1 = new CollisionObjectWrapper(obj1);

        btCollisionAlgorithm algorithm = dispatcher.findAlgorithm(co0.wrapper, co1.wrapper, null, 0);

        btDispatcherInfo info = new btDispatcherInfo();
        btManifoldResult result = new btManifoldResult(co0.wrapper, co1.wrapper);
        
        algorithm.processCollision(co0.wrapper, co1.wrapper, info, result);

        boolean r = result.getPersistentManifold().getNumContacts()>0;

        dispatcher.freeCollisionAlgorithm(algorithm.getCPointer());
        result.dispose();
        info.dispose();
        co0.dispose();
        co1.dispose();

        return r;
    }

    public boolean collisionWithGround(Player player, Array<Box> groundVector, float delta){
        boolean groundCollision=false;
        boolean currentCollision;
        for(int a=0;a<groundVector.size; a++){
            currentCollision = checkCollision(player.getObject(), groundVector.get(a).getObject());
            if(currentCollision==true){
                player.setGroundLevel(groundVector.get(a).getTop());
            }
            groundCollision = groundCollision || currentCollision;
        }

        if(!groundCollision){
            player.fall(delta);
        }else{
            player.hitTheGround(delta);
        }

        return groundCollision;
    }

    public void collisionWithEnemy(Player player, ArrayList<Enemy> enemyVector, Vector<ModelInstance> instances){
        for(int a=0;a<enemyVector.size();a++){
            if(checkCollision(player.getObject(), enemyVector.get(a).getObject()) && player.getImmunity()==true){
                instances.remove(enemyVector.get(a).getModel());
                player.reduceHealth();
                player.setImmunity(5);
                player.bounceBack();
            }
        }
        player.reduceImmunity();
    }

    public void collisionWithBullets(Player player, ArrayList<BulletEntity> bulletVec, Vector<ModelInstance> instances){
        for(int a=0;a<bulletVec.size();a++){
            if(checkCollision(player.getObject(), bulletVec.get(a).getObject()) && player.getImmunity()==true){
                instances.remove(bulletVec.get(a).getModel());
                bulletVec.remove(a);
                player.reduceHealth();
                player.setImmunity(5);
                //player.bounceBackBullets();
            }
        }
        player.reduceImmunity();
    }

    public void collisionWithBulletsAndSkull(Skull skull, ArrayList<BulletEntityPlayer> bulletVector, Vector<ModelInstance> instances){
        System.out.println("hello " + bulletVector.size());
        for(int a=0;a<bulletVector.size();a++){
            if(checkCollision(skull.getObject(), bulletVector.get(a).getObject())){
                System.out.println("STRIKE poop");
                instances.remove(bulletVector.get(a).getModel());
                bulletVector.remove(a);
                skull.reduceHealth();
            }
        }
    }

    public int collisionWithCoinAndPlayer(Player player, Vector<Coin> coinArray, Vector<ModelInstance> instances){
        for(int a=0;a<coinArray.size();a++){
            if(checkCollision(player.getObject(), coinArray.get(a).getObject())){
                instances.remove(coinArray.get(a).getModel());
                coinArray.remove(a);
                return 10;
            }
        }

        return 0;
    }
}