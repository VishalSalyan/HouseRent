package com.example.houserent.firebaseRepo;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.houserent.data.FaqData;
import com.example.houserent.data.HouseData;
import com.example.houserent.data.FavouriteHouseData;
import com.example.houserent.data.UserData;
import com.example.houserent.utils.Constants;
import com.example.houserent.utils.SessionData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class FireBaseRepo {
    public static final FireBaseRepo I = new FireBaseRepo();

    private FireBaseRepo() {
    }

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference userRef = database.getReference(Constants.USER);
    private DatabaseReference bunglowsCollectionRef = database.getReference(Constants.BUNGLOWS_COLLECTION);
    private DatabaseReference flatsCollectionRef = database.getReference(Constants.FLATS_COLLECTION);
    private DatabaseReference villaCollectionRef = database.getReference(Constants.VILLA_COLLECTION);
    private DatabaseReference faqRef = database.getReference(Constants.FAQ);


    //File Storage
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference mStorageReference = storage.getReference();

    public void signUp(final UserData userData, final ServerResponse<Boolean> serverResponse) {
        userRef.push().setValue(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                serverResponse.onSuccess(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                serverResponse.onFailure(new Throwable(e.getMessage()));
            }
        });
    }

    public void login(final String email, final String password, final ServerResponse<UserData> serverResponse) {
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserData user = snapshot.getValue(UserData.class);
                    assert user != null;
                    if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                        serverResponse.onSuccess(user);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                serverResponse.onFailure(new Throwable(databaseError.getMessage()));
            }
        });
    }

    public void fetchExploreCar(final ServerResponse<ArrayList<HouseData>> serverResponse) {
        bunglowsCollectionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<HouseData> carList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    HouseData carData = snapshot.getValue(HouseData.class);
                    carList.add(carData);
                }
                serverResponse.onSuccess(carList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                serverResponse.onFailure(new Throwable(databaseError.getMessage()));
            }
        });
    }

    public void fetchNewCar(final ServerResponse<ArrayList<HouseData>> serverResponse) {
        flatsCollectionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<HouseData> carList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    HouseData carData = snapshot.getValue(HouseData.class);
                    carList.add(carData);
                }
                serverResponse.onSuccess(carList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                serverResponse.onFailure(new Throwable(databaseError.getMessage()));
            }
        });
    }

    public void fetchCollection(final ServerResponse<ArrayList<HouseData>> serverResponse) {
        villaCollectionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<HouseData> carList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    HouseData carData = snapshot.getValue(HouseData.class);
                    carList.add(carData);
                }
                serverResponse.onSuccess(carList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                serverResponse.onFailure(new Throwable(databaseError.getMessage()));
            }
        });
    }

    public void setProfile(final UserData userData, final ServerResponse<String> serverResponse) {
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserData user = snapshot.getValue(UserData.class);
                    assert user != null;
                    if (user.getEmail().equals(userData.getEmail())) {
                        userRef.orderByChild("email").equalTo(user.getEmail()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                    String key = childSnapshot.getKey();
                                    assert key != null;
                                    userRef.child(key).setValue(userData);
                                }
                                serverResponse.onSuccess("Update Successfully");
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                serverResponse.onFailure(new Throwable(databaseError.getMessage()));
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getCarDetails(final String id, String mode, final ServerResponse<HouseData> serverResponse) {
        switch (mode) {
            case Constants.VILLA_COLLECTION:
                villaCollectionRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            HouseData carData = snapshot.getValue(HouseData.class);
                            assert carData != null;
                            if (carData.getId().equals(id)) {
                                serverResponse.onSuccess(carData);
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        serverResponse.onFailure(new Throwable(databaseError.getMessage()));
                    }
                });
                break;
            case Constants.BUNGLOWS_COLLECTION:
                bunglowsCollectionRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            HouseData carData = snapshot.getValue(HouseData.class);
                            assert carData != null;
                            if (carData.getId().equals(id)) {
                                serverResponse.onSuccess(carData);
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        serverResponse.onFailure(new Throwable(databaseError.getMessage()));
                    }
                });
                break;
            case Constants.FLATS_COLLECTION:
                flatsCollectionRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            HouseData carData = snapshot.getValue(HouseData.class);
                            assert carData != null;
                            if (carData.getId().equals(id)) {
                                serverResponse.onSuccess(carData);
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        serverResponse.onFailure(new Throwable(databaseError.getMessage()));
                    }
                });
                break;
        }
    }

    public void searchCar(final ServerResponse<String> serverResponse) {
        SessionData.getInstance().totalCarList.clear();
        bunglowsCollectionRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    HouseData carData = snapshot.getValue(HouseData.class);
                    SessionData.getInstance().totalCarList.add(carData);
                }
                serverResponse.onSuccess("Explore Car Added");
                flatsCollectionRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            HouseData carData = snapshot.getValue(HouseData.class);
                            SessionData.getInstance().totalCarList.add(carData);
                        }
                        serverResponse.onSuccess("New Car Added");

                        villaCollectionRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    HouseData carData = snapshot.getValue(HouseData.class);
                                    SessionData.getInstance().totalCarList.add(carData);
                                }
                                serverResponse.onSuccess("Collection Car Added");
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                serverResponse.onFailure(new Throwable(databaseError.getMessage()));

                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        serverResponse.onFailure(new Throwable(databaseError.getMessage()));
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                serverResponse.onFailure(new Throwable(databaseError.getMessage()));
            }
        });
    }

    public void wishListHouses(final String email, final ServerResponse<ArrayList<FavouriteHouseData>> serverResponse) {
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserData userData = snapshot.getValue(UserData.class);
                    if (userData.getEmail().equals(email)) {
                        serverResponse.onSuccess(userData.getFavouriteHouse());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                serverResponse.onFailure(new Throwable(databaseError.getMessage()));
            }
        });
    }

    public void fetchFaq(final ServerResponse<ArrayList<FaqData>> serverResponse) {
        faqRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<FaqData> faqList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    FaqData faqData = snapshot.getValue(FaqData.class);
                    faqList.add(faqData);
                }
                serverResponse.onSuccess(faqList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                serverResponse.onFailure(new Throwable(databaseError.getMessage()));
            }
        });
    }

    public void setWishListCars(final boolean isDeleteMode, final String email, final String houseId, final String houseMode, final ServerResponse<String> serverResponse) {
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    final UserData userData = snapshot.getValue(UserData.class);
                    assert userData != null;
                    if (userData.getEmail().equals(email)) {
                        final String key = snapshot.getKey();
                        assert key != null;

                        ArrayList<FavouriteHouseData> carWishList = new ArrayList<>();
                        if (!isDeleteMode) {
                            FavouriteHouseData wishListData = new FavouriteHouseData();
                            wishListData.setHouseId(houseId);
                            wishListData.setMode(houseMode);
                            carWishList.add(wishListData);
                            carWishList.addAll(SessionData.getInstance().getLocalData().getFavouriteHouse());
                            userRef.child(key).child("wishListHouses").setValue(carWishList);
                            getUserData(email, new ServerResponse<UserData>() {
                                @Override
                                public void onSuccess(UserData body) {
                                    serverResponse.onSuccess(email);
                                }

                                @Override
                                public void onFailure(Throwable error) {
                                }
                            });
                        } else {
                            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    ArrayList<FavouriteHouseData> wishList = new ArrayList<>();
                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        UserData userData1 = snapshot.getValue(UserData.class);
                                        assert userData1 != null;
                                        for (int i = 0; i < userData1.getFavouriteHouse().size(); i++) {
                                            if (!userData1.getFavouriteHouse().get(i).getHouseId().equals(houseId)) {
                                                wishList.add(userData1.getFavouriteHouse().get(i));
                                            }
                                        }
                                    }
                                    final ArrayList<FavouriteHouseData> finalWishList = new ArrayList<>(wishList);
                                    userRef.child(key).child("wishListHouses").setValue(null).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            userRef.child(key).child("wishListHouses").setValue(finalWishList);
                                            getUserData(email, new ServerResponse<UserData>() {
                                                @Override
                                                public void onSuccess(UserData body) {
                                                    userRef.child(key).child("wishListHouses").setValue(body.getFavouriteHouse());
                                                    serverResponse.onSuccess(email);
                                                }

                                                @Override
                                                public void onFailure(Throwable error) {

                                                }
                                            });
                                        }
                                    });
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    serverResponse.onFailure(new Throwable(databaseError.toString()));
                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                serverResponse.onFailure(new Throwable(databaseError.getMessage()));
            }
        });
    }

    public void getUserData(final String email, final ServerResponse<UserData> serverResponse) {
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserData userData = snapshot.getValue(UserData.class);

                    if (userData.getEmail().equals(email)) {
                        SessionData.getInstance().saveLocalData(userData);
                        serverResponse.onSuccess(userData);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                serverResponse.onFailure(new Throwable(databaseError.getMessage()));
            }
        });
    }

    public void uploadFile(String fileNameWithExtension, Uri data, final ServerResponse<String> serverResponse) {
        final StorageReference sRef = mStorageReference.child(fileNameWithExtension);
        sRef.putFile(data).addOnSuccessListener(taskSnapshot ->
                sRef.getDownloadUrl().addOnSuccessListener(uri ->
                        serverResponse.onSuccess(uri.toString())))
                .addOnFailureListener(exception ->
                        serverResponse.onFailure(new Throwable(exception.getMessage())))
                .addOnProgressListener(taskSnapshot ->
                {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
//                        textViewStatus.setText((int) progress + "% Uploading...");
                });
    }

}
