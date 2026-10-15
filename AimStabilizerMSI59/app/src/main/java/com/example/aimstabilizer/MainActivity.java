package com.example.aimstabilizer;

import android.app.Activity;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.*;

public class MainActivity extends Activity {
    private SharedPreferences prefs;
    private LinearLayout root;
    private TextView smoothValue, sensValue, dpiValue, status;
    private SeekBar smoothBar, sensBar, dpiBar;

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        prefs = getSharedPreferences("aim_settings", MODE_PRIVATE);
        buildUi();
        loadValues();
    }

    private int dp(float n) {
        return (int)(n * getResources().getDisplayMetrics().density + 0.5f);
    }

    private TextView text(String s, int size, int color, boolean bold) {
        TextView t = new TextView(this);
        t.setText(s); t.setTextSize(size); t.setTextColor(color);
        if (bold) t.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        return t;
    }

    private void buildUi() {
        ScrollView scroll = new ScrollView(this);
        scroll.setBackgroundColor(Color.rgb(16,17,26));
        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(20), dp(24), dp(20), dp(24));
        scroll.addView(root);
        setContentView(scroll);

        TextView title = text("AIM STABILIZER", 24, Color.WHITE, true);
        root.addView(title);
        TextView sub = text("MSI App Player 5.9 • Free Fire", 14, Color.rgb(174,177,196), false);
        LinearLayout.LayoutParams sp = new LinearLayout.LayoutParams(-1, -2);
        sp.bottomMargin = dp(18); sub.setLayoutParams(sp); root.addView(sub);

        LinearLayout note = new LinearLayout(this);
        note.setOrientation(LinearLayout.VERTICAL);
        note.setPadding(dp(14), dp(12), dp(14), dp(12));
        note.setBackgroundColor(Color.rgb(34,35,51));
        TextView nt = text("PAINEL DE AJUSTES", 14, Color.rgb(193,188,255), true);
        note.addView(nt);
        TextView nb = text("Este painel salva suas preferências e ajuda a organizar os testes. Ele não altera diretamente a mira nem controla o mouse dentro do emulador.", 13, Color.WHITE, false);
        LinearLayout.LayoutParams nbp = new LinearLayout.LayoutParams(-1,-2);
        nbp.topMargin = dp(6); nb.setLayoutParams(nbp); note.addView(nb);
        LinearLayout.LayoutParams np = new LinearLayout.LayoutParams(-1,-2);
        np.bottomMargin = dp(20); note.setLayoutParams(np); root.addView(note);

        smoothValue = addSlider("Suavização desejada", "Menor = resposta mais direta; maior = movimento mais filtrado.", 0, 100, 35, "smooth");
        sensValue = addSlider("Sensibilidade de referência", "Use como anotação para seus testes no jogo.", 1, 100, 50, "sens");
        dpiValue = addSlider("DPI do mouse (referência)", "Valor de referência; não altera o DPI físico do mouse.", 400, 16000, 800, "dpi");

        TextView prof = text("PERFIS RÁPIDOS", 15, Color.WHITE, true);
        LinearLayout.LayoutParams pp = new LinearLayout.LayoutParams(-1,-2);
        pp.topMargin = dp(22); pp.bottomMargin = dp(10); prof.setLayoutParams(pp); root.addView(prof);

        LinearLayout buttons = new LinearLayout(this);
        buttons.setOrientation(LinearLayout.HORIZONTAL);
        addProfileButton(buttons, "Preciso", 20, 42, 800);
        addProfileButton(buttons, "Equilibrado", 40, 50, 1200);
        addProfileButton(buttons, "Suave", 65, 45, 800);
        root.addView(buttons);

        Button save = new Button(this);
        save.setText("Salvar configurações");
        save.setAllCaps(false);
        LinearLayout.LayoutParams bp = new LinearLayout.LayoutParams(-1, -2);
        bp.topMargin = dp(18); save.setLayoutParams(bp);
        save.setOnClickListener(v -> {
            prefs.edit()
                .putInt("smooth", smoothBar.getProgress())
                .putInt("sens", sensBar.getProgress() + 1)
                .putInt("dpi", dpiBar.getProgress() + 400)
                .apply();
            status.setText("Configurações salvas neste dispositivo.");
        });
        root.addView(save);

        status = text("Ajuste uma opção e teste no campo de treinamento.", 13, Color.rgb(174,177,196), false);
        LinearLayout.LayoutParams stp = new LinearLayout.LayoutParams(-1,-2);
        stp.topMargin = dp(10); status.setLayoutParams(stp); root.addView(status);

        TextView footer = text("Dica: se a mira treme, teste uma sensibilidade menor no emulador e confira a aceleração do mouse no Windows. Mude uma opção por vez.", 13, Color.rgb(174,177,196), false);
        LinearLayout.LayoutParams fp = new LinearLayout.LayoutParams(-1,-2);
        fp.topMargin = dp(22); footer.setLayoutParams(fp); root.addView(footer);
    }

    private TextView addSlider(String title, String desc, int min, int max, int initial, String key) {
        TextView heading = text(title, 16, Color.WHITE, true);
        LinearLayout.LayoutParams hp = new LinearLayout.LayoutParams(-1,-2);
        hp.topMargin = dp(14); heading.setLayoutParams(hp); root.addView(heading);
        TextView description = text(desc, 12, Color.rgb(174,177,196), false);
        root.addView(description);

        TextView value = text("", 14, Color.rgb(193,188,255), true);
        LinearLayout.LayoutParams vp = new LinearLayout.LayoutParams(-1,-2);
        vp.topMargin = dp(5); value.setLayoutParams(vp); root.addView(value);
        SeekBar bar = new SeekBar(this);
        bar.setMax(max-min);
        bar.setProgress(initial-min);
        root.addView(bar);
        bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar b, int p, boolean user) {
                value.setText(String.valueOf(p + min));
            }
            public void onStartTrackingTouch(SeekBar b) {}
            public void onStopTrackingTouch(SeekBar b) {}
        });
        if (key.equals("smooth")) { smoothBar=bar; smoothValue=value; }
        if (key.equals("sens")) { sensBar=bar; sensValue=value; }
        if (key.equals("dpi")) { dpiBar=bar; dpiValue=value; }
        return value;
    }

    private void addProfileButton(LinearLayout row, String name, int smooth, int sens, int dpi) {
        Button b = new Button(this);
        b.setText(name); b.setAllCaps(false);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(0, dp(48), 1);
        p.setMargins(dp(2), 0, dp(2), 0); b.setLayoutParams(p);
        b.setOnClickListener(v -> {
            smoothBar.setProgress(smooth);
            sensBar.setProgress(sens - 1);
            dpiBar.setProgress(dpi - 400);
            status.setText("Perfil '" + name + "' selecionado. Salve para guardar.");
        });
        row.addView(b);
    }

    private void loadValues() {
        smoothBar.setProgress(prefs.getInt("smooth", 35));
        sensBar.setProgress(prefs.getInt("sens", 50) - 1);
        dpiBar.setProgress(prefs.getInt("dpi", 800) - 400);
    }
}
