package org.kwork4;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.google.android.gms.ads.AdRequest;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.kwork4.databinding.FragmentStickerBinding;
import org.kwork4.network.StickerPack;

import java.util.ArrayList;
import java.util.List;


public class StickerFragment extends Fragment {

    private FragmentStickerBinding binding;
    private StickerPack stickerPack;
    private IconsAdapter adapter;
    private CustomSpinnerAdapter spinnerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stickerPack = (StickerPack) getArguments().getSerializable("sticker");
        adapter = new IconsAdapter(stickerPack.getFields().getList()==null ? new ArrayList<>() : stickerPack.getFields().getList(),getActivity());
        List<String> list = new ArrayList<>();
        if(stickerPack.getFields().getVb()!=null) list.add(getString(R.string.vb));
        if(stickerPack.getFields().getTg()!=null) list.add(getString(R.string.tg));
        if(stickerPack.getFields().getOk()!=null) list.add(getString(R.string.ok));
        //spinValues = new String[]{getString(R.string.vb), getString(R.string.tg),getString(R.string.ok)};
        spinnerAdapter = new CustomSpinnerAdapter(getContext(),R.layout.spinner_item,list.toArray(new String[list.size()]),stickerPack);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        binding = FragmentStickerBinding.inflate(inflater,container,false);
        AdRequest request = new AdRequest.Builder().build();
        binding.adView.loadAd(request);
        ((TitleListener)getActivity()).update(stickerPack.getFields().getTitle());
        binding.setSticker(stickerPack);
        if(stickerPack.getFields().getAftor_link()!=null) {
            binding.authorStick.setText(
                    Html.fromHtml(String.format("Автор: <a href=\"%s\">%s</a>",stickerPack.getFields().getAftor_link(),stickerPack.getFields().getAftor()),
                            Html.FROM_HTML_MODE_COMPACT)
            );
            binding.authorStick.setLinkTextColor(getResources().getColor(R.color.teal_200,getContext().getTheme()));
            binding.authorStick.setMovementMethod(LinkMovementMethod.getInstance());
        } else {
            binding.authorStick.setText(String.format("Автор: %s",stickerPack.getFields().getAftor()));
        }
        //Linkify.addLinks(binding.authorStick,Linkify.WEB_URLS);
        if(stickerPack.getFields().getAttachments()!=null) {
            Picasso.get().load(stickerPack.getFields().getAttachments().get(0).getUrl()).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    binding.logoStick.setImageBitmap(bitmap);
                }

                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                    binding.logoStick.setImageDrawable(placeHolderDrawable);
                }
            });
        }
        binding.listStick.setAdapter(adapter);
        binding.listStick.setHasFixedSize(true);
        /*binding.button.setOnClickListener(view -> {
            if(stickerPack.getFields().getVb()==null) return;
            Intent intent = new Intent(Intent.ACTION_VIEW);
            //intent.setPackage("com.viber");
            intent.setData(Uri.parse(stickerPack.getFields().getVb()));
            startActivity(intent);
        });*/
        binding.spinner.setAdapter(spinnerAdapter);
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return binding.getRoot();
    }
    private String[] spinValues;
    private boolean first = true;

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.sticker_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_SUBJECT, "Sharing URL");
        i.putExtra(Intent.EXTRA_TEXT, stickerPack.getFields().getVb());
        startActivity(i);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        adapter.stop();
        super.onDestroyView();
    }
}