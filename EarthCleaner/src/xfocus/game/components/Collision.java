package xfocus.game.components;


import java.util.ArrayList;

import android.util.Log;

/*
 * 绾扮増锟斤拷濡拷濞达拷
 */
public class Collision {
	private ArrayList<DropThing> allDt;
	private int screenwidth;
	public Collision(ArrayList<DropThing> allDt,int sreenW){
		this.allDt = allDt;
		this.screenwidth=sreenW;
	}
	
	public int getScreenWidth() {
		return screenwidth;
	}
	
	// 濡拷濞达拷娑撱倗锟解晙锟斤拷锟斤拷锟斤拷锟斤拷绾扮増锟斤拷
	public boolean isCollision(DropThing dt) {
		for(int i=0;i<allDt.size();i++){
			if(allDt.get(i).equals(dt)){
			}else{
				if(Math.sqrt(Math.pow(allDt.get(i).getDropThingX()-dt.getDropThingX(), 2)
						+Math.pow(allDt.get(i).getDropThingY()-dt.getDropThingY(), 2))
						<=dt.getRadius()+allDt.get(i).getRadius()){
					return true;
				}
			}
		}
		return false;
	}
	
	
	
	// 濡拷濞达拷锟斤拷鈺�锟斤拷锟斤拷锟斤拷锟斤拷鐞氾拷锟斤拷鍫曪拷锟芥禍锟�
	public int isCollected(DropThing dt) {
		if(dt.getDropThingX()-dt.getRadius()<=0){
			return 1;
		}else if(dt.getDropThingX()+dt.getRadius()>=screenwidth){
			return 2;
		}else return 0;
	}
	
	
	/*
	 锟斤拷锟芥稉锟芥担锟斤拷锟藉府锟斤拷锟斤拷璺猴拷锟斤拷锟斤拷锟斤拷锟界喊鐗堬拷锟斤拷锟斤拷娑撹桨锟斤拷娑擄拷瀹革拷锟斤拷鍐诧拷锟斤拷锟斤拷
	 娑擄拷娑擄拷锟斤拷锟芥稉锟斤拷锟斤拷锟斤拷鎴掞拷锟界喊鐗堬拷锟�   瀹革拷锟斤拷宕囷拷锟芥稉锟斤拷锟斤拷锟斤拷鐣岋拷鐗堬拷锟� 锟斤拷璺猴拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷婢讹拷锟斤拷锟�
	*/
	/*public void isCollision(ArrayList<DropThing> allDt) {
		if(allDt.size()!=0){
			//锟斤拷锟芥稉锟芥担锟斤拷锟斤拷
			int kx=0,ky=0,i,min,k;
			for(i=0;i<allDt.size();i++){
				kx=kx+allDt.get(i).getDropThingX();
				ky=ky+allDt.get(i).getDropThingY();
			}
			kx=kx/allDt.size();
			ky=ky/allDt.size();
			k=Math.abs(kx-allDt.get(0).getDropThingX())+Math.abs(ky-allDt.get(0).getDropThingY());
			min=0;
			for(i=1;i<allDt.size();i++){
				if(k>Math.abs(kx-allDt.get(i).getDropThingX())+Math.abs(ky-allDt.get(i).getDropThingY())){
					k=Math.abs(kx-allDt.get(i).getDropThingX())+Math.abs(ky-allDt.get(i).getDropThingY());
					min=i;
				}
			}
			ArrayList<DropThing> top=new ArrayList<DropThing>();
			ArrayList<DropThing> buttom=new ArrayList<DropThing>();
			ArrayList<DropThing> left=new ArrayList<DropThing>();
			ArrayList<DropThing> right=new ArrayList<DropThing>();
			for(i=0;i<allDt.size();i++){
				if(i!=min){
					//锟斤拷锟藉锟斤拷锟芥湹锟斤拷娑擄拷瀹革拷锟斤拷宕囷拷锟斤拷锟解晛鑸�
					if(allDt.get(min).getDropThingY()>=allDt.get(i).getDropThingY()
							&&allDt.get(min).getDropThingY()>=allDt.get(i).getDropThingY()
							+allDt.get(i).getDropThingHeight()){
						top.add(allDt.get(i));
					}else if(allDt.get(min).getDropThingY()<=allDt.get(i).getDropThingY()
							&&allDt.get(min).getDropThingY()+
							allDt.get(min).getDropThingHeight()<=allDt.get(i).getDropThingY()
							){
						buttom.add(allDt.get(i));
					}else if(allDt.get(min).getDropThingX()>=allDt.get(i).getDropThingX()
							&&allDt.get(min).getDropThingX()>=allDt.get(i).getDropThingX()
							+allDt.get(i).getDropThingWidth()){
						left.add(allDt.get(i));
					}else if(allDt.get(min).getDropThingX()<=allDt.get(i).getDropThingX()
							&&allDt.get(min).getDropThingX()+
							allDt.get(min).getDropThingWidth()<=allDt.get(i).getDropThingX()
							){
						right.add(allDt.get(i));
					}else{
						allDt.get(i).setStatus(DropThing.CRASH);
						allDt.get(min).setStatus(DropThing.CRASH);
					}
				}
			}
			//鐎碉拷top锟斤拷锟絣eft
			for(i=0;i<top.size();i++){
				for(k=0;k<left.size();k++){
					if(top.get(i).getDropThingY()>=left.get(k).getDropThingY()
							&&top.get(i).getDropThingY()>=left.get(k).getDropThingY()
							+left.get(k).getDropThingHeight()){
						continue;
					}else if(top.get(i).getDropThingY()<=left.get(k).getDropThingY()
							&&top.get(i).getDropThingY()
							+top.get(i).getDropThingHeight()<=left.get(k).getDropThingY()){
						continue;
					}else if(top.get(i).getDropThingX()>=left.get(k).getDropThingX()
							&&top.get(i).getDropThingX()>=left.get(k).getDropThingX()
							+left.get(k).getDropThingWidth()){
						continue;
					}else if(top.get(i).getDropThingX()<=left.get(k).getDropThingX()
							&&top.get(i).getDropThingX()
							+top.get(i).getDropThingWidth()<=left.get(k).getDropThingX()){
						continue;
					}else{
						top.get(i).setStatus(DropThing.CRASH);
						left.get(k).setStatus(DropThing.CRASH);
					}
				}
			}
			
			//鐎碉拷buttom锟斤拷锟絣eft
			for(i=0;i<buttom.size();i++){
				for(k=0;k<left.size();k++){
					if(buttom.get(i).getDropThingY()>=left.get(k).getDropThingY()
							&&buttom.get(i).getDropThingY()>=left.get(k).getDropThingY()
							+left.get(k).getDropThingHeight()){
						continue;
					}else if(buttom.get(i).getDropThingY()<=left.get(k).getDropThingY()
							&&buttom.get(i).getDropThingY()
							+buttom.get(i).getDropThingHeight()<=left.get(k).getDropThingY()){
						continue;
					}else if(buttom.get(i).getDropThingX()>=left.get(k).getDropThingX()
							&&buttom.get(i).getDropThingX()>=left.get(k).getDropThingX()
							+left.get(k).getDropThingWidth()){
						continue;
					}else if(buttom.get(i).getDropThingX()<=left.get(k).getDropThingX()
							&&buttom.get(i).getDropThingX()
							+buttom.get(i).getDropThingWidth()<=left.get(k).getDropThingX()){
						continue;
					}else{
						buttom.get(i).setStatus(DropThing.CRASH);
						left.get(k).setStatus(DropThing.CRASH);
					}
				}
			}
			
			//鐎碉拷top锟斤拷锟絩ight
			for(i=0;i<top.size();i++){
				for(k=0;k<right.size();k++){
					if(top.get(i).getDropThingY()>=right.get(k).getDropThingY()
							&&top.get(i).getDropThingY()>=right.get(k).getDropThingY()
							+right.get(k).getDropThingHeight()){
						continue;
					}else if(top.get(i).getDropThingY()<=right.get(k).getDropThingY()
							&&top.get(i).getDropThingY()
							+top.get(i).getDropThingHeight()<=right.get(k).getDropThingY()){
						continue;
					}else if(top.get(i).getDropThingX()>=right.get(k).getDropThingX()
							&&top.get(i).getDropThingX()>=right.get(k).getDropThingX()
							+right.get(k).getDropThingWidth()){
						continue;
					}else if(top.get(i).getDropThingX()<=right.get(k).getDropThingX()
							&&top.get(i).getDropThingX()
							+top.get(i).getDropThingWidth()<=right.get(k).getDropThingX()){
						continue;
					}else{
						top.get(i).setStatus(DropThing.CRASH);
						right.get(k).setStatus(DropThing.CRASH);
					}
				}
			}
			
			
			//鐎碉拷buttom锟斤拷锟絩ight
			for(i=0;i<buttom.size();i++){
				for(k=0;k<right.size();k++){
					if(buttom.get(i).getDropThingY()>=right.get(k).getDropThingY()
							&&buttom.get(i).getDropThingY()>=right.get(k).getDropThingY()
							+right.get(k).getDropThingHeight()){
						continue;
					}else if(buttom.get(i).getDropThingY()<=right.get(k).getDropThingY()
							&&buttom.get(i).getDropThingY()
							+buttom.get(i).getDropThingHeight()<=right.get(k).getDropThingY()){
						continue;
					}else if(buttom.get(i).getDropThingX()>=right.get(k).getDropThingX()
							&&buttom.get(i).getDropThingX()>=right.get(k).getDropThingX()
							+right.get(k).getDropThingWidth()){
						continue;
					}else if(buttom.get(i).getDropThingX()<=right.get(k).getDropThingX()
							&&buttom.get(i).getDropThingX()
							+buttom.get(i).getDropThingWidth()<=right.get(k).getDropThingX()){
						continue;
					}else{
						buttom.get(i).setStatus(DropThing.CRASH);
						right.get(k).setStatus(DropThing.CRASH);
					}
				}
			}
			//鐎碉拷top
			for(i=0;i<top.size();i++){
				for(k=0;k<top.size();k++){
					
				}
			}
			//鐎碉拷buttom
			for(i=0;i<buttom.size();i++){
				for(k=0;k<buttom.size();k++){
					
				}
			}
			//鐎碉拷left
			for(i=0;i<left.size();i++){
				for(k=0;k<left.size();k++){
					
				}
			}
			//鐎碉拷right
			for(i=0;i<right.size();i++){
				for(k=0;k<right.size();k++){
					
				}
			}
			
		}
	}*/
	
	/*
	public static void main(String[] args) {
		ArrayList<DropThing> allDt=new ArrayList<DropThing>();
		for(int i=0;i<20;i++){
			DropThing dt=new DropThing();
			dt.setDropThingX((int)(Math.random()*400));
			dt.setDropThingY((int)(Math.random()*400));
			dt.setRadius(((int)(Math.random()*200)));
			allDt.add(dt);
		}
		Collision c=new Collision(allDt);
		long start = System.currentTimeMillis();
		System.out.println(start);
		for(int j=0;j<20;j++)
		for(int i=0;i<allDt.size();i++){
			c.isCollision(allDt.get(i));
		}
		long end = System.currentTimeMillis();
		System.out.println(end);
	}*/
}
