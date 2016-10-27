package com.MinimalSoft.FAR.Tabs.Measurements;

import android.app.Dialog;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.MinimalSoft.FAR.R;
import com.angel.sdk.BleScanner;
import com.angel.sdk.BluetoothInaccessibleException;

import junit.framework.Assert;

public class ConnectFragment extends Fragment implements View.OnClickListener {

    private View inflatedView;
    private static final int IDLE = 0;
    private static final int SCANNING = 1;
    private static final int CONNECTED = 2;

    private BleScanner mBleScanner;
    private RelativeLayout mControl;
    private TextView mControlAction;

    private Dialog mDeviceListDialog;
    private ListItemsAdapter mDeviceListAdapter;

    static private int sState = IDLE;

    BleScanner.ScanCallback mScanCallback = new BleScanner.ScanCallback() {
        @Override
        public void onBluetoothDeviceFound(BluetoothDevice device) {
            if (device.getName() != null && device.getName().startsWith("Angel")) {
                ListItem newDevice = new ListItem(device.getName(), device.getAddress(), device);
                mDeviceListAdapter.add(newDevice);
                mDeviceListAdapter.addItem(newDevice);
                mDeviceListAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (inflatedView == null) {
            inflatedView = inflater.inflate(R.layout.fragment_connect, container, false);

            mControl = (RelativeLayout) inflatedView.findViewById(R.id.control);
            mControl.setOnClickListener(this);

            mControlAction = (TextView) inflatedView.findViewById(R.id.controlAction);

            mDeviceListAdapter = new ListItemsAdapter(getContext(), R.layout.list_item);
        }
        return inflatedView;
    }

    @Override
    public void onResume() {
        super.onResume();

        setControlActionText();
    }

    @Override
    public void onClick(View v) {
        switch (sState) {
            case IDLE:
                startScan();
                break;
            case SCANNING:
                stopScan();
                break;
            case CONNECTED:
//            disconnect();
                break;
        }
        setControlActionText();
    }

    private void startScan() {

        // Initialize the of the Ble Scanner.
        try {
            if (mBleScanner == null) {

                mBleScanner = new BleScanner(getActivity(), mScanCallback);
            }
        } catch (BluetoothInaccessibleException e) {
            throw new AssertionError("Bluetooth is not accessible");
        }


        sState = SCANNING;
        mBleScanner.startScan();
        showDeviceListDialog();
    }


    private void stopScan() {
        if (sState == SCANNING) {
            mBleScanner.stopScan();
            sState = IDLE;
        }
    }

    private void setControlActionText() {
        switch (sState) {
            case IDLE:
                mControlAction.setText(R.string.scan);
                break;
            case SCANNING:
                mControlAction.setText(R.string.scanning);
                break;
            case CONNECTED:
                mControlAction.setText(R.string.disconnect);
                break;
        }
    }

    private void showDeviceListDialog() {
        mDeviceListDialog = new Dialog(getContext());
        mDeviceListDialog.setTitle("Select A Device");
        mDeviceListDialog.setContentView(R.layout.device_list);
        ListView lv = (ListView) mDeviceListDialog.findViewById(R.id.lv);
        lv.setAdapter(mDeviceListAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View item, int position, long id) {
                stopScan();
                mDeviceListDialog.dismiss();

                BluetoothDevice bluetoothDevice = mDeviceListAdapter.getItem(position).getBluetoothDevice();
                Assert.assertTrue(bluetoothDevice != null);
                Intent intent = new Intent(parent.getContext(), mes.class);
                intent.putExtra("ble_device_address", bluetoothDevice.getAddress());
                startActivity(intent);
            }
        });

        mDeviceListDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                stopScan();
            }
        });
        mDeviceListDialog.show();
    }

    public void onStop() {
        super.onStop();
        stopScan();
    }

}