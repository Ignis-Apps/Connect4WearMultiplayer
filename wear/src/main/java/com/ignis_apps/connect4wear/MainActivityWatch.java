package com.ignis_apps.connect4wear;

        import android.os.Bundle;
        import android.support.annotation.NonNull;
        import android.support.wearable.activity.WearableActivity;
        import android.util.Log;
        import android.widget.Toast;

        import com.google.android.gms.tasks.Task;
        import com.google.android.gms.wearable.DataClient;
        import com.google.android.gms.wearable.DataEvent;
        import com.google.android.gms.wearable.DataEventBuffer;
        import com.google.android.gms.wearable.DataItem;
        import com.google.android.gms.wearable.PutDataRequest;
        import com.google.android.gms.wearable.Wearable;
        import com.ignis_apps.connect4wear.Game.OpernGL.OpenGLPanel;
        import com.ignis_apps.connect4wear.Game.Scenes.GameInterface;

public class MainActivityWatch extends WearableActivity implements DataClient.OnDataChangedListener ,GameInterface{

    private DataClient dataClient;
    private OpenGLPanel panel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        panel = new OpenGLPanel(this,this);
        setContentView(panel);

        dataClient = Wearable.getDataClient(this);
        dataClient.addListener(this);

    }

    public void setBoardState(byte[] data){

        int[][] board = new int[6][5];
        String d = new String(data);

        char[] ch = d.toCharArray();

        int row_index = 0;
        int colum_index = 0;

        for(char c:ch){
            switch (c){
                case ':':
                    row_index++;
                    break;
                case ';':
                    row_index=0;
                    colum_index++;
                    break;
                default:
                   // System.out.println("c " + colum_index+" r: " + row_index + " value" + c);
                    board[colum_index][row_index] = Integer.parseInt(""+c);
                    break;
            }
        }

        panel.getRenderer().getGame().getBoard().setBoard(board);

        if((panel.getRenderer().getGame().getBoard().stones.size()%2)==0){
            panel.getRenderer().getGame().getControlStone().setIsRed(false);
        }else {
            panel.getRenderer().getGame().getControlStone().setIsRed(true);
        }

        panel.getRenderer().getGame().setLockInput(false);
        panel.requestRender();

    }

    public byte[] getBoardData(int[][] board){

        String out ="";
        for(int[] c:board){

            for(int i = 0;i<c.length;i++){
                out+=(c[i]+":");
            }
            out+=";";
        }
        return out.getBytes();

    }


    @Override
    protected void onStart() {
        super.onStart();
        dataClient.addListener(this);
        sendDataItem("/launch", Long.toBinaryString(System.currentTimeMillis()).getBytes());
    }

    @Override
    protected void onStop() {
        super.onStop();
        dataClient.removeListener(this);
    }

    private void sendDataItem(String path, byte[] data) {
        PutDataRequest putDataReq = PutDataRequest.create(path);
        putDataReq.setUrgent();
        putDataReq.setData(data);
        Task<DataItem> putDataTask = dataClient.putDataItem(putDataReq);
    }

    @Override
    public void onDataChanged(@NonNull DataEventBuffer dataEventBuffer) {
        DataEvent event = dataEventBuffer.get(dataEventBuffer.getCount()-1);

            if (event.getType() == DataEvent.TYPE_CHANGED) {
                // DataItem changed
                DataItem item = event.getDataItem();

                if(item.getUri().getPath().equals("/taketurn")){
                    setBoardState(item.getData());
                }

                Toast.makeText(this, item.getUri().getPath(), Toast.LENGTH_SHORT).show();

                Log.d("ignis_log", "TYPE_CHANGED: " + item.getUri().getPath() + " / " + new String(item.getData()));
            } else if (event.getType() == DataEvent.TYPE_DELETED) {
                // DataItem deleted
            }

    }

    @Override
    public void onPlayerMadeHisMove(int[][] board) {

        panel.getRenderer().getGame().setLockInput(true);
        byte[] d = getBoardData(board);
        sendDataItem("/tookturn", d);

    }

    @Override
    public void onPlayerChangedStonePosition(int position) {

    }

    @Override
    public void onGameCompleted(int player_who_won) {
        sendDataItem("/finished", (""+player_who_won + " / " + Long.toBinaryString(System.currentTimeMillis())).getBytes());
    }
}
