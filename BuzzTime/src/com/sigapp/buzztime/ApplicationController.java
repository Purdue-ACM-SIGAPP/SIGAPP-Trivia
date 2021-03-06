package com.sigapp.buzztime;

import java.util.Observable;
import java.util.Observer;

import android.app.Application;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;
import edu.purdue.cs.Message;
import edu.purdue.cs.RoomConnection;

public class ApplicationController extends Application {

	private RoomConnection room;
	
	public static String userName;
	public static String roomName;
	public static String uid;
	
	private char lastAnswer;
	
	public ApplicationController(String userName, String roomName, Context c) {
		// Get a unique id for the phone
		TelephonyManager tManager = (TelephonyManager)c.getSystemService(Context.TELEPHONY_SERVICE);
		String uid = tManager.getDeviceId();
		
		// Set static variables
		this.userName = userName;
		this.roomName = roomName;
		this.uid = uid;
		
		
		room = new RoomConnection(roomName, new Observer() {
			public void update(Observable arg0, Object arg1) {
				Message m = (Message) arg1;
				Log.d("Incoming message", m.getText());
				
			}
		});
	}
	
	public void sendAnswer(char answer) {
		this.lastAnswer = answer;
		Message toSend = new Message("user: "+ApplicationController.userName+" "+answer);
		room.sendMessage(toSend);
	}
	
}
