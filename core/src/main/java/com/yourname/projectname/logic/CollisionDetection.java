package com.yourname.projectname.logic;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector;
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

    public void collisionWithEnemy(Player player, ArrayList<Enemy> enemyVector){
        for(int a=0;a<enemyVector.size();a++){
            if(checkCollision(player.getObject(), enemyVector.get(a).getObject()) && player.getImmunity()==true){
                player.reduceHealth();
                player.setImmunity(5);
                player.bounceBack();
            }
        }
        player.reduceImmunity();
    }
}