package de.traijan.farbcode;

import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.content.ContextCompat;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView tvOutput;
    TextView ringOne, ringTwo, ringThree, ringFour, ringFifth;
    Spinner sp1, sp2, sp3, spMulti, spTolerance;
    Button button;

    int[] colorRings;
    List<Float> multiplicator;
    List<Float> toleranz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Verweise zu den Views zuweisen über die ID
        tvOutput = findViewById(R.id.tv_output);
        sp1 = findViewById(R.id.sp_1);
        sp2 = findViewById(R.id.sp_2);
        sp3 = findViewById(R.id.sp_3);
        spMulti = findViewById(R.id.sp_4);
        spTolerance = findViewById(R.id.sp_5);
        button = findViewById(R.id.button);

        ringOne = findViewById(R.id.ringOne);
        ringTwo = findViewById(R.id.ringTwo);
        ringThree = findViewById(R.id.ringThree);
        ringFour = findViewById(R.id.ringFour);
        ringFifth = findViewById(R.id.ringFifth);

        // Spinner konfigurieren
        setSpinner(sp1, R.array.spinner_1_text);
        setSpinner(sp2, R.array.spinner_2_text);
        setSpinner(sp3, R.array.spinner_3_text);
        setSpinner(spMulti, R.array.spinner_multi_text);
        setSpinner(spTolerance, R.array.spinner_toleranz_text);

        // Variablen zuweisen
        colorRings = getResources().getIntArray(R.array.spinner_1_values);
        multiplicator = getFloatList(R.array.spinner_multi_values);
        toleranz = getFloatList(R.array.spinner_toleranz_values);
    }

    /** Berechnet den Widerstand
     * @param v Die View
     */
    public void calculate(View v) {
        // Alle Spinner-Item Positionen speichern
        int firstColorRing = sp1.getSelectedItemPosition();
        int secondColorRing = sp2.getSelectedItemPosition();
        int thirdColorRing = sp3.getSelectedItemPosition();
        int multiplier = spMulti.getSelectedItemPosition();
        int tolerance = spTolerance.getSelectedItemPosition();

        // -1 ist der Name des Rings selber, quasi wie nichts gesetzt.
        if(colorRings[firstColorRing] == -1) {
            Toast.makeText(this, getString(R.string.chooseFirstColorRing), Toast.LENGTH_SHORT).show();
            return;
        }
        else if(colorRings[secondColorRing] == -1) {
            Toast.makeText(this, getString(R.string.chooseSecondColorRing), Toast.LENGTH_SHORT).show();
            return;
        }
        else if(multiplicator.get(multiplier) == -1) {
            Toast.makeText(this, getString(R.string.chooseMultiplicator), Toast.LENGTH_SHORT).show();
            return;
        }
        else if(toleranz.get(tolerance) == -1 && colorRings[thirdColorRing] != -1) { // Wenn die Toleranz nicht gesetzt ist, aber der 3. Farbring
            Toast.makeText(this, getString(R.string.chooseToleranz), Toast.LENGTH_SHORT).show();
            return;
        }

        double number; // Die Zahl die multipliziert gehört später

        // Wenn der Dritte Farbring den Wert -1 bringt wurde dieser nicht gesetzt => nur 4 Ringe Rechnung
        if(colorRings[thirdColorRing] == -1)
            number = Integer.parseInt("" + colorRings[firstColorRing] + colorRings[secondColorRing]);
        else // 5 Ringe
            number = Integer.parseInt("" + colorRings[firstColorRing] + colorRings[secondColorRing] + colorRings[thirdColorRing]);

        // Wenn die Toleranz -1 ist (nicht gesetzt) dann setzen wir sie auf 0 (Nur 3 Ringe), ansonsten ist sie dann der eigentliche Wert
        double tol = toleranz.get(tolerance) == -1 ? 0 : toleranz.get(tolerance);

        number *= multiplicator.get(multiplier);

        DecimalFormat df = new DecimalFormat("#.##"); // Objekt zum formatieren auf 2 Nachkommastellen
        String formatted = df.format(number); // Formatiert
        String cleanFormatted = formatted.replace(',', '.'); // Manchmal kommen statt 0.00 0,00 und damit wird immer auf 0.00 getan
        double formattedDouble = Double.parseDouble(cleanFormatted); // In double speichern
        tvOutput.setText(String.format(getString(R.string.result), formatNumber(formattedDouble), "" + df.format(tol)) + "%");
    }

    /** Erstellt aus der angebenen ID eine Float List
     * @param id Der Name des Array Values
     * @return Die Float List
     */
    List<Float> getFloatList(int id) {
        String[] cacheString = getResources().getStringArray(id); // String Array erstellen aus der Ressource
        List<Float> floatList = new ArrayList<Float>(); // Aus de

        for(String s : cacheString)
            floatList.add(Float.parseFloat(s));

        return floatList;
    }

    /** Entscheidet welche Farbe als TextView Hintergrund gesetzt wird
     * @param t Die TextView dessen Hintergrund gesetzt werden soll
     * @param color Die Farbe als String Wert
     */
    public void drawRect(TextView t, String color) {
        String s = color.toLowerCase();

        if(s.equals(getString(R.string.black).toLowerCase()))
            t.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
        else if(s.equals(getString(R.string.brown).toLowerCase()))
            t.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.brown));
        else if(s.equals(getString(R.string.red).toLowerCase()))
            t.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
        else if(s.equals(getString(R.string.orange).toLowerCase()))
            t.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.orange));
        else if(s.equals(getString(R.string.yellow).toLowerCase()))
            t.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.yellow));
        else if(s.equals(getString(R.string.green).toLowerCase()))
            t.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
        else if(s.equals(getString(R.string.blue).toLowerCase()))
            t.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.blue));
        else if(s.equals(getString(R.string.violet).toLowerCase()))
            t.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.violett));
        else if(s.equals(getString(R.string.gray).toLowerCase()))
            t.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.gray));
        else if(s.equals(getString(R.string.white).toLowerCase()))
            t.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        else if(s.equals(getString(R.string.gold).toLowerCase()))
            t.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.gold));
        else if(s.equals(getString(R.string.silver).toLowerCase()))
            t.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.silver));
        else
            t.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.transparent));
    }

    /** Formatiert die Zahl zu einem lesbareren String
     * @param count Die zu formatierenden Zahl
     * @return Die formatierte Zahl als String
     */
    public String formatNumber(double count) { // TPE
        if (count < 1000) return "" + count;
        int exp = (int) (Math.log(count) / Math.log(1000));
        return String.format("%.2f %c", count / Math.pow(1000, exp),"kMG".charAt(exp-1));
    }

    /** Konfiguriert den Spinner
     * @param sp Der zu konfigurierende Spinner
     * @param textArrayId Die String-Array ID
     */
    public void setSpinner(Spinner sp, int textArrayId) {
        // ArrayAdapter erstellen
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, textArrayId, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Dropdown konfigurieren
        sp.setAdapter(adapter); // Adapter danach Spinner zuweisen
        sp.setOnItemSelectedListener(this); // OnItemSelectListener setzen (Damit die Methode darunter ausgeführt wird von dem Spinner)
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // Entscheidet welcher Spinner ausgewählt wurde und leitet daraus die zu übergebene TextView

        if(adapterView == sp1)
            drawRect(ringOne, (String)adapterView.getItemAtPosition(i));
        else if(adapterView == sp2)
            drawRect(ringTwo, (String)adapterView.getItemAtPosition(i));
        else if(adapterView == sp3)
            drawRect(ringThree, (String)adapterView.getItemAtPosition(i));
        else if(adapterView == spMulti)
            drawRect(ringFour, (String)adapterView.getItemAtPosition(i));
        else if(adapterView == spTolerance)
            drawRect(ringFifth, (String)adapterView.getItemAtPosition(i));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
