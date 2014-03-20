/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mouglotte.test;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;

import com.mouglotte.genetics.Genetics;
import com.mouglotte.specy.Desire;
import com.mouglotte.specy.DesireType;
import com.mouglotte.specy.Desires;
import com.mouglotte.specy.Mouglotte;
import com.mouglotte.specy.Need;
import com.mouglotte.specy.NeedType;
import com.mouglotte.specy.Needs;
import com.mouglotte.ui.ProgressBar;

/**
 *
 * @author A178414
 */
public class TestDecisionUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	Mouglotte mouglotte;
	Thread thread;
	Needs needs;
	Desires desires;
	
    public TestDecisionUI() {
        
    	Genetics.initialize();
    	
    	initComponents();
        
        Need need;
		Desire desire;

		//this.mouglotte = new Mouglotte();
		this.needs = new Needs(this.mouglotte);
		this.desires = new Desires(this.mouglotte);

		need = new Need(this.needs, NeedType.HUNGER, 150, 75, 50, 40, 12);
		need.setGeneValue(100);
		this.needs.put(need);

		desire = new Desire(this.desires, DesireType.HUNGER, 600, 300, 450, 200, 12);
		desire.setGeneValue(100);
		desire.setRelatedNeed(need);
		this.desires.put(desire);

		need = new Need(this.needs, NeedType.REST, 150, 75, 50, 40, 12);
		need.setGeneValue(100);
		this.needs.put(need);

		desire = new Desire(this.desires, DesireType.REST, 600, 300, 450, 200, 12);
		desire.setGeneValue(100);
		desire.setRelatedNeed(need);
		this.desires.put(desire);

		need = new Need(this.needs, NeedType.SOCIAL, 150, 75, 50, 40, 12);
		need.setGeneValue(100);
		this.needs.put(need);

		desire = new Desire(this.desires, DesireType.SOCIAL, 600, 300, 450, 200, 12);
		desire.setGeneValue(100);
		desire.setRelatedNeed(need);
		this.desires.put(desire);
		
		createTimer();
    }

    private void initComponents() {

    	Container myPane = getContentPane();
    	myPane.setLayout(new GridLayout(4, 4));
    	setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    	
    	btTimer = new javax.swing.JButton("Timer");
    	txtTimer = new javax.swing.JTextField();
        txtCurrNeed = new javax.swing.JTextField();
        txtCurrNeedVal = new ProgressBar(0, 1000);
        txtCurrNeedState = new javax.swing.JTextField();
        txtCurrDesire = new javax.swing.JTextField();
        txtCurrDesireVal = new ProgressBar(0, 1000);
        txtCurrDesireState = new javax.swing.JTextField();
        txtChoice = new javax.swing.JTextField();
        btFulfill = new javax.swing.JButton("Fulfill");
        
        myPane.add(btTimer);
        myPane.add(txtCurrNeed);
        myPane.add(txtCurrNeedVal);
        myPane.add(txtCurrNeedState);
    	
    	myPane.add(txtTimer);
    	myPane.add(txtCurrDesire);
    	myPane.add(txtCurrDesireVal);
    	myPane.add(txtCurrDesireState);
    	
        myPane.add(txtChoice);
        myPane.add(btFulfill);
        myPane.add(new JLabel("plop"));
        myPane.add(new JLabel("plop"));
        myPane.add(new JLabel("plop"));
    	  	
        txtTimer.setText("timer");
        txtCurrNeed.setText("currNeed");
        txtCurrNeedVal.setValue(0);
        txtCurrNeedState.setText("state");
        txtCurrDesire.setText("currDesire");
        txtCurrDesireVal.setValue(0);
        txtCurrDesireState.setText("value");
        txtChoice.setText("choice");
        
        btTimer.addActionListener(this);
        btFulfill.addActionListener(this);
        
        pack();
        setVisible(true);
    }


	private void createTimer() {

		final int timeFactor = 60;
		// long startTime = System.currentTimeMillis();
		// long currTime = startTime;
		// long elapsedTime = System.currentTimeMillis() - currTime;

		this.thread = new Thread() {
			int minutes = 50;
			int hours = 0;
			boolean running = true;

			@Override
			public void run() {

				while (running) {
					// Evénement minute
					testEventMinute(minutes);
					minutes++;

					// Evénement heure
					if (minutes >= 60) {
						testEventHour();
						minutes = 0;
						hours++;
					}
					
					try {
						// 1 minute mouglotte = 3 secondes rï¿½elles
						sleep(3000 / timeFactor);
					} catch (Exception e) {
					}
				}
			}
		};
	}

   
	private void startTimer() {
		if (this.thread == null)
			createTimer();
		this.thread.start();
		setTxtTimer("running...");
	}

	private void stopTimer() {
		if (this.thread == null)
			createTimer();
		this.thread.interrupt();
		setTxtTimer("stopped");
	}

	private boolean timerRunning() {

		switch (this.thread.getState()) {
		case RUNNABLE:
		case BLOCKED:
		case WAITING:
		case TIMED_WAITING:
			return true;
		case NEW:
		case TERMINATED:
			return false;
		}

		if (this.thread != null)
			return !this.thread.isInterrupted();

		else
			return false;
	}

	private void testEventMinute(int minutes) {

		this.needs.fulfill();
		this.desires.fulfill();

		this.txtTimer.setText(Integer.toString(minutes));
		displayCurrent();
	}

	private void testEventHour() {

		this.needs.decide();
		this.desires.decide();

		if (this.needs.getCurrent().getValue() > this.desires.getCurrent()
				.getValue()) {
			this.needs.setSearching(true);
			this.desires.setSearching(false);
			this.desires.setFulfilling(false);
			setTxtChoice("need");
		} else {
			this.desires.setSearching(true);
			this.needs.setSearching(false);
			this.needs.setFulfilling(false);
			setTxtChoice("desire");			
		}

		displayCurrent();
	}

	private void displayCurrent() {
		
		if (this.needs.getCurrent() != null) {
			setTxtCurrNeed(this.needs.getCurrent().getType()
					.toString());
			setTxtCurrNeedVal(this.needs.getCurrent().getValue());
		}
		if (this.desires.getCurrent() != null) {
			setTxtCurrDesire(this.desires.getCurrent().getType()
					.toString());
			setTxtCurrDesireVal(this.desires.getCurrent()
					.getValue());
		}
		
		if (this.needs.isSearching()) 
			setTxtCurrNeedState("searching");
		else if (this.needs.isFulfilling()) 
			setTxtCurrNeedState("fulfilling");
		else 
			setTxtCurrNeedState("waiting");
		if (this.desires.isSearching()) 
			setTxtCurrDesireState("searching");
		else if (this.desires.isFulfilling()) 
			setTxtCurrDesireState("fulfilling");
		else 
			setTxtCurrDesireState("waiting");
	}
	
	private void fulfillMoi() {
		
		if (this.needs.isSearching())
			this.needs.setFulfilling(true);
		if (this.desires.isSearching())
			this.desires.setFulfilling(true);
	}

	
	
    private void setTxtCurrNeed(String value) {
        this.txtCurrNeed.setText(value);
    }

    private void setTxtCurrNeedVal(int value) {
        this.txtCurrNeedVal.setValue(value);
    }
    
    private void setTxtCurrNeedState(String value) {
        this.txtCurrNeedState.setText(value);
    }

    private void setTxtCurrDesire(String value) {
        this.txtCurrDesire.setText(value);
    }

    private void setTxtCurrDesireVal(int value) {
        this.txtCurrDesireVal.setValue(value);
    }

    private void setTxtCurrDesireState(String value) {
        this.txtCurrDesireState.setText(value);
    }
    
    private void setTxtTimer(String value) {
        this.txtTimer.setText(value);
    }
    
    private void setTxtChoice(String value) {
        this.txtChoice.setText(value);
    }
    
    @Override
	public void actionPerformed(ActionEvent arg0) {
  	
        if (arg0.getSource() == btTimer) {
        	if (timerRunning())
        		stopTimer();
        	else
        		startTimer();
        }
        
        if (arg0.getSource() == btFulfill) {
        	fulfillMoi();
        }
    }
    
    public static void main(String args[]) {
    	
    	new TestDecisionUI();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btTimer;
    private javax.swing.JButton btFulfill;
    private javax.swing.JTextField txtChoice;
    private javax.swing.JTextField txtCurrDesire;
    private javax.swing.JTextField txtCurrDesireState;
    private ProgressBar txtCurrDesireVal;
    private javax.swing.JTextField txtCurrNeed;
    private javax.swing.JTextField txtCurrNeedState;
    private ProgressBar txtCurrNeedVal;
    private javax.swing.JTextField txtTimer;
    // End of variables declaration//GEN-END:variables
}
