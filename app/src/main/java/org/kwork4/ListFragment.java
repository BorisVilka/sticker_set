package org.kwork4;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DividerItemDecoration;

import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import org.kwork4.databinding.FragmentListBinding;
import org.kwork4.network.StickerPack;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;
import io.reactivex.rxjava3.core.SingleOnSubscribe;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class ListFragment extends Fragment {

    private FragmentListBinding binding;
    private FilterAdapter filterAdapter;
    private PagedAdapter adapter;
    private ListAdapter listAdapter;
    private PagedViewModel viewModel;
    private InterstitialAd mInterstitialAd;
    private boolean b;
    private int count = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("TAG","CREATE !");
        super.onCreate(savedInstanceState);
        listAdapter = new ListAdapter((stickerPack -> {
            NavController navController = Navigation.findNavController(getActivity(),R.id.nav_host_fragment_activity_main);
            Bundle args = new Bundle();
            args.putSerializable("sticker",stickerPack);
            navController.navigate(R.id.stickerFragment,args);
        }),new ArrayList<>());
        filterAdapter = new FilterAdapter((id)->{
            Log.d("TAG",adapter.getCurrentList().stream().collect(Collectors.toList()).size()+" "+filterAdapter.empty());
            // adapter.requestFilter(id);
            if(filterAdapter.empty()) {
                Log.d("TAG","EMPTY");
                listAdapter.requestFilter(id);
                binding.list.setAdapter(adapter);
            } else {
                listAdapter.setList(adapter.getCurrentList().stream().collect(Collectors.toList()));
                listAdapter.requestFilter(id);
                binding.list.setAdapter(listAdapter);
            }
        },getContext());
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(PagedViewModel.class);
        adapter = viewModel.getAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        Log.d("TAG","CREATE");
        binding = FragmentListBinding.inflate(inflater,container,false);
        AdRequest request = new AdRequest.Builder()
                .build();
        binding.adView2.loadAd(request);
        binding.categories.setAdapter(filterAdapter);
        binding.list.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        b = true;
        binding.search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().length()==0) {
                   if(b) {
                       b = false;
                       return;
                   } else {
                       binding.list.setAdapter(adapter);
                   }
                } else {
                    listAdapter.setList(adapter.getCurrentList().stream().collect(Collectors.toList()));
                    listAdapter.setText(charSequence.toString());
                    binding.list.setAdapter(listAdapter);
                }
                //adapter.setText(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        Log.d("TAG",filterAdapter.empty()+" "+listAdapter.getItemCount());
        if(filterAdapter.empty()) binding.list.setAdapter(adapter);
        else binding.list.setAdapter(listAdapter);
        return binding.getRoot();
    }
    static class MainThreadExecutor implements Executor {
        private final Handler mHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(Runnable command) {
            mHandler.post(command);
        }
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.main_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        NavController controller = Navigation.findNavController(getActivity(),R.id.nav_host_fragment_activity_main);
        controller.navigate(R.id.preferencesFragment);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("TAG","DESTROY VIew");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("TAG","DESTROY");
    }
    //
    @Override
    public void onStart() {
        super.onStart();
        count++;
        if(count%10==0) {
            AdRequest adRequest = new AdRequest.Builder().build();

            InterstitialAd.load(getContext(),"ca-app-pub-6591947822910170/5873894508", adRequest,
                    new InterstitialAdLoadCallback() {
                        @Override
                        public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                            // The mInterstitialAd reference will be null until
                            // an ad is loaded.
                            mInterstitialAd = interstitialAd;
                            mInterstitialAd.show(getActivity());
                            Log.i("TAG", "onAdLoaded");
                        }

                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            // Handle the error
                            Log.d("TAG", loadAdError.toString());
                            mInterstitialAd = null;
                        }
                    });
        }
    }
}