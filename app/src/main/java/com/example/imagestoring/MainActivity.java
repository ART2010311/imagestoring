package com.example.imagestoring;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.activity.result.ActivityResultLauncher;

import androidx.activity.result.contract.ActivityResultContracts;



import com.google.firebase.storage.FirebaseStorage;

import com.google.firebase.storage.StorageReference;
import android.net.Uri;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ImageView image;
    Button selectImage,uploadImage;
    StorageReference reference;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image=findViewById(R.id.imageView2);
        selectImage=findViewById(R.id.button);
        uploadImage=findViewById(R.id.button2);
         reference=FirebaseStorage.getInstance().getReference().child("images");
        ActivityResultLauncher<String>imageSelector=registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                result ->{
                    if(result != null){
                        imageUri=result;
                        image.setImageURI(imageUri);
                    }
                });
        selectImage.setOnClickListener(view -> imageSelector.launch("image/*"));
        uploadImage.setOnClickListener(view ->{
            if(imageUri !=null){
                StorageReference storageReference=reference.child(String.valueOf(System.currentTimeMillis()));
                storageReference.putFile(imageUri).addOnCompleteListener(task ->{
                    if(task.isSuccessful()){
                        Toast.makeText(MainActivity.this,"Successfully uploaded",Toast.LENGTH_SHORT).show();
                        image.setImageResource(R.drawable.ic_baseline_image_24);

                    }else{
                        Toast.makeText(MainActivity.this,"Please select an image",Toast.LENGTH_SHORT).show();
                    }

                });
            }
        });
    }

}