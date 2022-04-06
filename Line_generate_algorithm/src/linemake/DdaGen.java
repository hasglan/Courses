package linemake;

import java.awt.*;
import java.awt.event.*;

public class DdaGen extends Frame implements ActionListener {
	Button dbtn = new Button("DDA");
	Button bbtn = new Button("Bres");
	Label xstart = new Label("X1: ",Label.CENTER);
	TextField xstartin = new TextField();
	Label ystart = new Label("Y1: ",Label.CENTER);
	TextField ystartin = new TextField();
	
	Label xend = new Label("X2: ",Label.CENTER);
	TextField xendin = new TextField();
	Label yend = new Label("Y2: ",Label.CENTER);
	TextField yendin = new TextField();
	
	DrawArea area = new DrawArea();
	
	Panel pnlUp;
	Panel pnlMiddle;
	Panel pnlDown;
	
	int xst,yst,xed,yed;
	
	public static void main(String [] args) {
		DdaGen myDda = new DdaGen();
			
		myDda.setTitle("Line Generate Al");
		myDda.setSize(860,720);
		myDda.setVisible(true);
	}
	
	public DdaGen() {
		pnlUp = new Panel(new GridLayout(1,4));
		pnlMiddle = new Panel(new BorderLayout());
		pnlDown = new Panel(new GridLayout(1,2));
		
		add(pnlUp, BorderLayout.NORTH);
		add(pnlMiddle, BorderLayout.CENTER);
		add(pnlDown, BorderLayout.SOUTH);
		
		pnlUp.add(xstart);
		pnlUp.add(xstartin);
		pnlUp.add(ystart);
		pnlUp.add(ystartin);
		pnlUp.add(xend);
		pnlUp.add(xendin);
		pnlUp.add(yend);
		pnlUp.add(yendin);
		pnlMiddle.add(area);
		pnlDown.add(dbtn);
		pnlDown.add(bbtn);
		
		dbtn.addActionListener(this);
		bbtn.addActionListener(this);
		
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
	
	public void actionPerformed(ActionEvent evt) {
		String str = evt.getActionCommand();
		
		if(str.equals("DDA")) {
			area.draw("dda");
		}else if(str.equals("Bres")) {
			area.draw("bres");
		};
	}
	
	class DrawArea extends Canvas{
		String shape = "";
		
		public void paint(Graphics g) {
			xst = Integer.valueOf(xstartin.getText());
			yst = Integer.valueOf(ystartin.getText());
			xed = Integer.valueOf(xendin.getText());
			yed = Integer.valueOf(yendin.getText());
			g.setColor(new Color(random(256),random(256),random(256)));
			
			if(shape.equals("dda")){
			    dda(g,xst,yst,xed,yed);
		    } else if(shape.equals("bres")) {
		    	bres(g,xst,yst,xed,yed);
		    }
		}

		void draw(String str) {
			shape = str;
			repaint();
		}

		private int random(int r) {
			return ((int)Math.floor(Math.random()*r));
		}
	}
	
	public void dda(Graphics g, int xst,int yst,int xed,int yed) {
			
		int dx = Math.abs(xed-xst), dy = Math.abs(yed-yst), steps, i;
		float xIncrement, yIncrement, x=xst, y=yst;
		
		if(Math.abs(dx)>Math.abs(dy))steps=Math.abs(dx);
		else steps=Math.abs(dy);
		xIncrement = dx/(float)steps; //x증가량
		yIncrement = dy/(float)steps; //y증가량
		
		int x1 = (int)(x);
		int y1 = (int)(y);
		g.drawLine(x1,y1,x1,y1); //시작점
		
		if(xst<xed&&yst<yed) {//1사분면
			for(i=0;i<steps;i++) {
				x+=xIncrement;
				y+=yIncrement;
				x1=(int)(x+0.5);
				y1=(int)(y+0.5);
				g.drawLine(x1,y1,x1,y1);
			}
		}else if(xst<xed&&yst>yed) {//2사분면
			for(i=0;i<steps;i++) {
				x+=xIncrement;
				y-=yIncrement;
				x1=(int)(x+0.5);
				y1=(int)(y-0.5);
				g.drawLine(x1,y1,x1,y1);
			}
		}else if(xst>xed&&yst>yed) {//3사분면
			for(i=0;i<steps;i++) {
				x-=xIncrement;
				y-=yIncrement;
				x1=(int)(x-0.5);
				y1=(int)(y-0.5);
				g.drawLine(x1,y1,x1,y1);
			}
		}else { //4사분면
			for(i=0;i<steps;i++) {
				x-=xIncrement;
				y+=yIncrement;
				x1=(int)(x-0.5);
				y1=(int)(y+0.5);
				g.drawLine(x1,y1,x1,y1);
			}
		}
	}
	public void bres(Graphics g,int xst,int yst,int xed,int yed) {
		int dx= Math.abs(xed-xst), dy=Math.abs(yed-yst);
		int p=2*dy-dx;
		int twoDx = 2*(dy-dx), twoDy = 2*dy;
		int x,y,end;
		
		if(xed>xst&&yed>yst) { //시작점 위치 변경
			x=xst;
			y=yst;
			end=xed;
		}else if(xed>xst&&yed<yst){
			x=xst;
			y=yed;
			end=xed;
		}else if(xed<xst&&yed>yst){
			x=xed;
			y=yst;
			end=xst;
		}else {
			x=xed;
			y=yed;
			end=xst;
		}
		g.drawLine(x,y,x,y);
		
		
		while(x<end) {
			x++;
			if(p<0)
				p+=twoDy;
			else {
				y++;
				p+=twoDx;
			}
			g.drawLine(x,y,x,y);		
			}
		}
	}

