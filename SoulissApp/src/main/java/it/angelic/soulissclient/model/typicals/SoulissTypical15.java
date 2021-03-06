package it.angelic.soulissclient.model.typicals;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import java.util.ArrayList;

import it.angelic.soulissclient.*;
import it.angelic.soulissclient.Constants;
import it.angelic.soulissclient.helpers.ListButton;
import it.angelic.soulissclient.helpers.SoulissPreferenceHelper;
import it.angelic.soulissclient.model.ISoulissCommand;
import it.angelic.soulissclient.model.ISoulissTypical;
import it.angelic.soulissclient.model.SoulissCommand;
import it.angelic.soulissclient.model.SoulissTypical;
import it.angelic.soulissclient.net.UDPHelper;

public class SoulissTypical15 extends SoulissTypical implements ISoulissTypical {

	// SoulissNode parentd = getParentNode();
	// SoulissTypical related =
	// parentd.getTypical((short)(getTypicalDTO().getSlot()+1));

	/**
	 * 
	 */
	private static final long serialVersionUID = 4553488985062542092L;

	// Context ctx;

	public SoulissTypical15(SoulissPreferenceHelper pp) {
		super(pp);
	}

	@Override
	public ArrayList<ISoulissCommand> getCommands(Context ctx) {
		// ritorna le bozze dei comandi, da riempire con la schermata addProgram
		ArrayList<ISoulissCommand> ret = new ArrayList<>();

		SoulissCommand t = new SoulissCommand(this);
		t.getCommandDTO().setCommand(Constants.Typicals.Souliss_T1n_RGB_OnCmd);
		t.getCommandDTO().setSlot(getTypicalDTO().getSlot());
		t.getCommandDTO().setNodeId(getTypicalDTO().getNodeId());
		ret.add(t);

		SoulissCommand ff = new SoulissCommand(this);
		ff.getCommandDTO().setCommand(Constants.Typicals.Souliss_T1n_RGB_OffCmd);
		ff.getCommandDTO().setSlot(getTypicalDTO().getSlot());
		ff.getCommandDTO().setNodeId(getTypicalDTO().getNodeId());
		ret.add(ff);

		return ret;
	}

	/**
	 * Ottiene il layout del pannello comandi
	 * 
	 */
	@Override
	public void getActionsLayout(final Context ctx,  final LinearLayout cont) {

		cont.removeAllViews();
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		
		cont.addView(getQuickActionTitle());
		/*
		 * 
		 * TRE BOTTONI: ON, OFF e TOGGLE
		 */
		final ListButton tog = new ListButton(ctx);
		// final int tpos = position;
		tog.setText("Rmt");
		tog.setLayoutParams(lp);
		cont.addView(tog);

		final ListButton turnON = new ListButton(ctx);
		turnON.setText("ON");
		turnON.setLayoutParams(lp);
		cont.addView(turnON);

		final ListButton turnOFF = new ListButton(ctx);
		turnOFF.setText("OFF");
		turnOFF.setLayoutParams(lp);
		cont.addView(turnOFF);

		tog.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Thread t = new Thread() {
					public void run() {
						Intent nodeDatail = new Intent(ctx, T15RGBIrActivity.class);
						nodeDatail.putExtra("TIPICO", SoulissTypical15.this);
						ctx.startActivity(nodeDatail);
					}
				};

				t.start();

			}

		});

		turnON.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//tog.setEnabled(false);
				//turnON.setEnabled(false);
				//turnOFF.setEnabled(false);
				Thread t = new Thread() {
					public void run() {
							UDPHelper.issueSoulissCommand("" + getTypicalDTO().getNodeId(), "" + typicalDTO.getSlot(),
									prefs,  String.valueOf(Constants.Typicals.Souliss_T1n_RGB_OnCmd));
					}
				};

				t.start();

			}

		});

		turnOFF.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//tog.setEnabled(false);
				//turnON.setEnabled(false);
				//turnOFF.setEnabled(false);
				Thread t = new Thread() {
					public void run() {
							UDPHelper.issueSoulissCommand("" + getTypicalDTO().getNodeId(), "" + typicalDTO.getSlot(),
									prefs, String.valueOf(Constants.Typicals.Souliss_T1n_RGB_OffCmd));
					}
				};

				t.start();

			}

		});

	}

	@Override
	public String getOutputDesc() {

		if (typicalDTO.getOutput() == Constants.Typicals.Souliss_T1n_RGB_OffCmd)
			return "OFF";
		else
			return "ON";
	}

}
