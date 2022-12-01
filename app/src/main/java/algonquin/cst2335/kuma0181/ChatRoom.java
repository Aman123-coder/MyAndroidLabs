package algonquin.cst2335.kuma0181;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.kuma0181.ChatMessageDao;
import algonquin.cst2335.kuma0181.MessageDatabase;
import algonquin.cst2335.kuma0181.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.kuma0181.databinding.ReceiveMessageBinding;
import algonquin.cst2335.kuma0181.databinding.SentMessageBinding;






public class ChatRoom extends AppCompatActivity {


        private ActivityChatRoomBinding binding;
        private RecyclerView.Adapter<MyRowHolder> myAdapter;
        ArrayList<ChatMessage> messages;
        ChatRoomViewModel chatModel;
    ChatMessageDao mDAO;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            binding = ActivityChatRoomBinding.inflate((getLayoutInflater()));
            setContentView(binding.getRoot());

            MessageDatabase db = Room.databaseBuilder(getApplicationContext(), MessageDatabase.class, "database-name").build();
            mDAO = db.cmDAO();

            chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
            messages = chatModel.messages.getValue();

            if (messages == null) {
                chatModel.messages.postValue(messages = new ArrayList<ChatMessage>());
            }
            //chatModel.messages.postValue(messages = new ArrayList<ChatMessage>());
            chatModel.messages.setValue(messages = new ArrayList<>());

            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() ->
            {
                messages.addAll( mDAO.getAllMessages() ); //Once you get the data from database

                runOnUiThread( () ->  binding.recycleView.setAdapter( myAdapter )); //You can then load the RecyclerView
            });

            binding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
                @NonNull
                @Override
                public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    if (viewType == 0) {
                        SentMessageBinding sendBinding = SentMessageBinding.inflate(getLayoutInflater());
                        return new MyRowHolder(sendBinding.getRoot());
                    } else {
                        ReceiveMessageBinding receiveBinding = ReceiveMessageBinding.inflate(getLayoutInflater());
                        return new MyRowHolder(receiveBinding.getRoot());
                    }

                }

                @Override
                public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                    holder.messageText.setText("");
                    holder.timeText.setText("");

                    ChatMessage obj = messages.get(position);
                    holder.messageText.setText(obj.getMessage());
                    holder.timeText.setText(obj.getTimeSent());
                }

                @Override
                public int getItemCount() {
                    return messages.size();
                }

                @Override
                public int getItemViewType(int position) {
                    if (messages.get(position).isSentButton() == true) {
                        return 0;
                    } else {
                        return 1;
                    }

                }
            });

            binding.sendButton.setOnClickListener(click -> {
                SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd-MMM-yyy hh-mm-ss a");
                String currentDateandTIme = sdf.format(new Date());
                messages.add(new ChatMessage(binding.textInput.getText().toString(), currentDateandTIme, true));
                myAdapter.notifyItemInserted(messages.size()-1);
                binding.textInput.setText("");
            });

            binding.receiveButton.setOnClickListener(click -> {
                SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd-MMM-yyy hh-mm-ss a");
                String currentDateandTIme = sdf.format(new Date());
                messages.add(new ChatMessage(binding.textInput.getText().toString(), currentDateandTIme, false));
                myAdapter.notifyItemInserted(messages.size()-1);
                binding.textInput.setText("");
            });
            binding.recycleView.setLayoutManager(new LinearLayoutManager(this));


        }
        class MyRowHolder extends RecyclerView.ViewHolder {
            TextView messageText;
            TextView timeText;

            public MyRowHolder(@NonNull View itemView) {
                super(itemView);

                itemView.setOnClickListener(clk ->{
                    int position = getAbsoluteAdapterPosition();

                    AlertDialog.Builder builder = new AlertDialog.Builder( ChatRoom.this );

                    builder.setMessage("Do you want to delete the message: " +messageText.getText())
                            .setTitle("Question: ")

                            .setNegativeButton("No", (dialog, cl) -> { })

                            .setPositiveButton("Yes", (dialog, cl) -> {

                                ChatMessage removedMessage = messages.get(position);
                                messages.remove(position);
                                myAdapter.notifyItemRemoved(position);


                                Snackbar.make(messageText, "You Deleted message #"+ position, Snackbar.LENGTH_LONG)
                                        .setAction("Undo", click -> {
                                            messages.add(position, removedMessage);
                                            myAdapter.notifyItemInserted(position);
                                        })
                                        .show();
                            })
                            .create()
                            .show();

                });

                messageText = itemView.findViewById(R.id.messageText);
                timeText = itemView.findViewById(R.id.timeText);
            }
        }
    }


