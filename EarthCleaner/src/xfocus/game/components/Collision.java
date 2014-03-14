package xfocus.game.components;


import java.util.ArrayList;

import android.util.Log;

/*
 * 纰版��妫�娴�
 */
public class Collision {
	private ArrayList<DropThing> allDt;
	private int sreenwidth;
	public Collision(ArrayList<DropThing> allDt,int sreenW){
		this.allDt = allDt;
		this.sreenwidth=sreenW;
	}
	
	/*public void addDropThing(DropThing dt) {
		allDt.add(dt);
	}*/

	// 妫�娴�涓ょ�╀��������纰版��
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
	
	
	
	// 妫�娴���╀��������琚���堕��浜�
	public int isCollected(DropThing dt) {
		if(dt.getDropThingX()-dt.getRadius()<=0){
			return 1;
		}else if(dt.getDropThingX()+dt.getRadius()>=sreenwidth){
			return 2;
		}else return 0;
	}
	
	
	/*
	 ���涓�浣���帮����跺��������纰版�����涓轰��涓�宸���冲�����
	 涓�涓����涓������戒��纰版��   宸���崇��涓������界�版�� ��跺�����������澶����
	*/
	/*public void isCollision(ArrayList<DropThing> allDt) {
		if(allDt.size()!=0){
			//���涓�浣����
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
					//���寰���朵��涓�宸���崇����╁舰
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
			//瀵�top���left
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
			
			//瀵�buttom���left
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
			
			//瀵�top���right
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
			
			
			//瀵�buttom���right
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
			//瀵�top
			for(i=0;i<top.size();i++){
				for(k=0;k<top.size();k++){
					
				}
			}
			//瀵�buttom
			for(i=0;i<buttom.size();i++){
				for(k=0;k<buttom.size();k++){
					
				}
			}
			//瀵�left
			for(i=0;i<left.size();i++){
				for(k=0;k<left.size();k++){
					
				}
			}
			//瀵�right
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
