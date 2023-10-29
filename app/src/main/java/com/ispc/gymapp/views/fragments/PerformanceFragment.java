package com.ispc.gymapp.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ispc.gymapp.R;
import android.widget.TextView;  // Agrega esta importación para TextView
import java.util.Collections;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PerformanceFragment extends Fragment {
    private Set<String> selectedDates = new HashSet<>();
    private FirebaseFirestore db;
    private CalendarView calendarView;
    private int selectedDateCount = 0;
    private List<View> selectedDayFrameLayouts = new ArrayList<>();
    private TextView selectedDateCountTextView;  // Agrega un TextView para mostrar el contador
    private int currentStreak = 0;
    private int bestStreak = 0;
    private TextView currentStreakTextView;
    private TextView bestStreakTextView;

    public PerformanceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_performance, container, false);
        currentStreakTextView = view.findViewById(R.id.currentStreakTextView);
        bestStreakTextView = view.findViewById(R.id.bestStreakTextView);

        // Carga la mejor racha almacenada
        loadBestStreak();

        db = FirebaseFirestore.getInstance();
        calendarView = view.findViewById(R.id.calendario);
        calendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            // Convierte la fecha seleccionada a millis
            Calendar selectedDate = Calendar.getInstance();
            selectedDate.set(year, month, dayOfMonth);
            long selectedDateMillis = selectedDate.getTimeInMillis();

            // Si la fecha ya está seleccionada, deseléala
            if (selectedDates.contains(String.valueOf(selectedDateMillis))) {
                selectedDates.remove(String.valueOf(selectedDateMillis));
                selectedDateCount--;

                // Borra el borde naranja
                View dayFrameLayout = view1.findViewById(R.id.dayFrameLayout);
                dayFrameLayout.setForeground(null);

                // Verifica la racha
                checkStreak();
            } else {
                // Si la fecha no está seleccionada, selecciónala
                selectedDates.add(String.valueOf(selectedDateMillis));
                selectedDateCount++;

                // Agrega el borde naranja
                View dayFrameLayout = view1.findViewById(R.id.dayFrameLayout);
                dayFrameLayout.setForeground(ContextCompat.getDrawable(requireContext(), R.drawable.selected_date_border));

                // Verifica la racha
                checkStreak();
            }

            // Actualiza el contador de fechas seleccionadas
            updateSelectedDateCount();
        });

        // Carga y muestra las fechas seleccionadas previamente
        loadSelectedDates();
        selectedDateCountTextView = view.findViewById(R.id.selectedDateCountTextView);

        // Carga la mejor racha almacenada
        loadBestStreak();

        return view;
    }
    private void checkStreak() {
        if (selectedDates.size() > 0) {
            List<Long> selectedDateMillisList = new ArrayList<>();
            for (String date : selectedDates) {
                selectedDateMillisList.add(Long.parseLong(date));
            }
            Collections.sort(selectedDateMillisList);

            currentStreak = 1; // Inicializa la racha actual como 1

            for (int i = 0; i < selectedDateMillisList.size() - 1; i++) {
                long currentDateMillis = selectedDateMillisList.get(i);
                long nextDateMillis = selectedDateMillisList.get(i + 1);

                Calendar currentCalendar = Calendar.getInstance();
                currentCalendar.setTimeInMillis(currentDateMillis);

                Calendar nextCalendar = Calendar.getInstance();
                nextCalendar.setTimeInMillis(nextDateMillis);

                // Verifica si las fechas son consecutivas
                if (isConsecutiveDays(currentCalendar, nextCalendar)) {
                    currentStreak++;
                } else {
                    break; // La racha se rompe
                }
            }

            // Actualiza la racha actual en la interfaz de usuario
            updateCurrentStreak();

            if (currentStreak > bestStreak) {
                // Actualiza la mejor racha
                bestStreak = currentStreak;
                updateBestStreak();
            }
        } else {
            currentStreak = 0;
            updateCurrentStreak();
        }
    }



    private void updateCurrentStreak() {
        // Actualiza el contador de racha actual en tu interfaz de usuario
        currentStreakTextView.setText(String.valueOf(currentStreak));
    }

    private void updateBestStreak() {
        // Actualiza el contador de mejor racha en tu interfaz de usuario
        bestStreakTextView.setText(String.valueOf(bestStreak));
    }

    private void loadBestStreak() {
        // Carga la mejor racha almacenada previamente desde donde la tengas guardada
        // y llama a updateBestStreak para actualizar la interfaz de usuario
    }

    private boolean isConsecutiveDays(Calendar date1, Calendar date2) {
        if (date1.get(Calendar.YEAR) == date2.get(Calendar.YEAR)) {
            if (date1.get(Calendar.MONTH) == date2.get(Calendar.MONTH)) {
                // Las fechas están en el mismo mes, verifica si los días son consecutivos
                int day1 = date1.get(Calendar.DAY_OF_MONTH);
                int day2 = date2.get(Calendar.DAY_OF_MONTH);
                return Math.abs(day1 - day2) == 1;
            } else if (Math.abs(date1.get(Calendar.MONTH) - date2.get(Calendar.MONTH)) == 1) {
                // Las fechas están en meses consecutivos, verifica si son el último día del mes y el primer día del siguiente mes
                int lastDayOfMonth1 = date1.getActualMaximum(Calendar.DAY_OF_MONTH);
                int firstDayOfMonth2 = 1;
                int day1 = date1.get(Calendar.DAY_OF_MONTH);
                int day2 = date2.get(Calendar.DAY_OF_MONTH);
                return (day1 == lastDayOfMonth1 && day2 == firstDayOfMonth2);
            }
        }
        return date1.get(Calendar.YEAR) == date2.get(Calendar.YEAR) &&
                date1.get(Calendar.MONTH) == date2.get(Calendar.MONTH) &&
                date1.get(Calendar.DAY_OF_MONTH) + 1 == date2.get(Calendar.DAY_OF_MONTH);


    }



    private void loadSelectedDates() {
        DocumentReference docRef = db.collection("selectedDates").document("userDates");

        docRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                List<String> loadedDates = (List<String>) documentSnapshot.get("dates");
                if (loadedDates != null) {
                    selectedDates = new HashSet<>(loadedDates);
                    // Actualiza tu interfaz de usuario o realiza otras acciones
                    updateCalendarBackground();
                    updateSelectedDateCount();
                }
            }
        }).addOnFailureListener(e -> {
            // Maneja el error
        });
    }

    private void updateCalendarBackground() {
        // Itera a través de las fechas seleccionadas y agrega el borde naranja
        for (String selectedDateMillis : selectedDates) {
            long millis = Long.parseLong(selectedDateMillis);
            Calendar selectedDate = Calendar.getInstance();
            selectedDate.setTimeInMillis(millis);
            View dayFrameLayout = calendarView.findViewById(R.id.dayFrameLayout);
            dayFrameLayout.setForeground(ContextCompat.getDrawable(requireContext(), R.drawable.selected_date_border));
        }
    }

    private void updateSelectedDateCount() {
        // Actualiza el contador de fechas seleccionadas en tu interfaz de usuario
        selectedDateCountTextView.setText(String.valueOf(selectedDateCount));
    }

    // Agrega el resto de tus métodos según tus necesidades

}
