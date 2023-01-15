package com.example.shareshelf;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;

        import android.app.AlertDialog;
        import android.app.ProgressDialog;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.WindowManager;
        import android.widget.Button;
        import android.widget.CheckBox;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.OnFailureListener;
        import com.google.android.gms.tasks.OnSuccessListener;
        import com.google.android.gms.tasks.Task;
        import com.google.android.material.dialog.MaterialAlertDialogBuilder;
        import com.google.firebase.auth.AuthResult;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
        import com.google.firebase.firestore.FirebaseFirestore;
        import com.google.firebase.firestore.Query;
        import com.google.firebase.firestore.QueryDocumentSnapshot;
        import com.google.firebase.firestore.QuerySnapshot;

public class Login extends AppCompatActivity {
    TextView createnewAccount, forgotPwd;
    EditText inputEmail, inputPassword;
    Button btnLogin;
    String emailPattern = "[a-zA-Z0-9._-]+@[[a-z]+\\.+[a-z]+]*";
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    AlertDialog.Builder reset_alert;
    LayoutInflater inflater;
    //TERMINI E CONDIZIONI
    private CheckBox checkBox;
    private MaterialAlertDialogBuilder materialAlertDialogBuilder;
    //TERMINI E CONDIZIONI


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        reset_alert = new AlertDialog.Builder(this);
        inflater = this.getLayoutInflater();

        createnewAccount = findViewById(R.id.createNewAccount);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        inputEmail=findViewById(R.id.inputEmail);
        inputPassword=findViewById(R.id.inputPassword);
        btnLogin=findViewById(R.id.btnLogin);
        forgotPwd = findViewById(R.id.forgotPassword);

        forgotPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //start alertdialog
                View view2 = inflater.inflate(R.layout.reset_pop, null);

                reset_alert.setTitle("Reset Forgot Password?")
                        .setMessage("Enter your email to get password reset link.")
                        .setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //validate the email address
                                EditText email = view2.findViewById(R.id.reset_email_pop);
                                if(email.getText().toString().isEmpty()){
                                    email.setError("Required Field");
                                    return;
                                }
                                //send the reset link
                                fAuth.sendPasswordResetEmail(email.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(Login.this, "Reset email sent", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        }).setNegativeButton("Cancel", null)
                        .setView(view2)
                        .create().show();
            }
        });



        createnewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, CreateUser.class));
                //TERMINI E CONDIZIONI
                getSupportActionBar().hide();
                //TERMINI E CONDIZIONI

                //TERMINI E CONDIZIONI
                //checkBox = findViewById(R.id.checkPrivacy);
                materialAlertDialogBuilder = new MaterialAlertDialogBuilder(Login.this);
                //btnNextPageRegistration.setEnabled(false);

                materialAlertDialogBuilder.setTitle("Informativa privacy");
                materialAlertDialogBuilder.setMessage("Informativa per il trattamento dei dati personali App ShareShelf\n" +
                        "Informativa per il trattamento dei dati personali ai sensi dell’art. 13 del Regolamento UE N. 2016/679 e art. 13 D.lgs. 196/2003\n" +
                        "\n" +
                        " \n" +
                        "\n" +
                        "Gentile Utente,\n" +
                        "\n" +
                        "La informiamo, ai sensi dell’art. 13 Regolamento UE n. 2016/679 (in seguito, “GDPR”) e dell’art.13 del D.Lgs. 30 Giugno 2003 n. 196 (‘Codice in materia di protezione dei dati personali’), di seguito ‘Codice della Privacy’, che i dati personali da Lei forniti potranno essere oggetto di trattamento, nel rispetto delle normative sopra richiamate e degli obblighi di riservatezza, con le modalità e per le finalità di seguito riportate.\n" +
                        "\n" +
                        " \n" +
                        "\n" +
                        "1.Titolare del trattamento\n" +
                        "\n" +
                        "COMFTECH S.r.l. con sede legale in Piazza Castello 196, Monza (MB); e-mail: info@comfetch.com (telefono: 0399008300) nella persona del suo legale rappresentante pro tempore garantisce il rispetto della disciplina in materia della protezione dei dati personali fornendo le seguenti informazioni circa il trattamento dei dati comunicati o comunque raccolti.\n" +
                        "\n" +
                        " \n" +
                        "\n" +
                        "2.Oggetto del trattamento\n" +
                        "\n" +
                        "Ai sensi dell’art. 4 punto 2 del Regolamento, per trattamento di dati personali si intende “qualsiasi operazione o insieme di operazioni, compiute con o senza l’ausilio di processi automatizzati e applicate a dati personali o insiemi di dati personali, come la raccolta, la registrazione, l’organizzazione, la strutturazione, la conservazione, l’adattamento o la modifica, l’estrazione, la consultazione, l’uso, la comunicazione mediante trasmissione, diffusione o qualsiasi altra forma di messa a disposizione, il raffronto o l’interconnessione, la limitazione, la cancellazione o la distruzione”.\n" +
                        "\n" +
                        "L’applicazione fornisce informazioni sullo sviluppo e sul benessere del bambino e fornisce agli utenti contenuti personalizzati, messaggi, consigli e altri servizi (di seguito “Servizi”). Tutti i dati verranno salvati (crittografati) nella piattaforma cloud e non risulteranno accessibili da utenti senza autorizzazione. Inoltre, i dati raccolti non verranno utilizzati per scopi commerciali o pubblicitari, o venduti a terze parti.\n" +
                        "\n" +
                        "Questa Informativa sulla privacy ha lo scopo di aiutare gli utenti che utilizzano questi Servizi a informarli sulla protezione dei dati e in merito alla privacy, inclusi quali dati vengono raccolti, perché sono raccolti e quali sono gli utilizzi, così come i diritti individuali degli utenti.\n" +
                        "\n" +
                        "L’applicazione utilizza i dati personali raccolti o trattati dall’applicazione mobile (di seguito “App”), cosi come i dati rilevati dal “wearable device” abbinato all’App.\n" +
                        "\n" +
                        "La presente Informativa sulla privacy si applica ai dati personali raccolti o trattati dalla Comftech S.r.l.\n" +
                        "\n" +
                        " \n" +
                        "\n" +
                        "3.Destinatari dei Dati Personali\n" +
                        "\n" +
                        "La informiamo che i Suoi dati saranno conservati e saranno comunicati esclusivamente ai soggetti competenti per l’espletamento dei servizi necessari ad una corretta gestione del rapporto, con garanzia di tutela dei diritti dell’interessato. I suoi dati saranno trattati, in particolare, dalle seguenti categorie di soggetti:\n" +
                        "\n" +
                        "dai dipendenti e dai collaboratori del Titolare, in qualità di persone autorizzate al trattamento;\n" +
                        "dalle Società terze o altri soggetti che svolgono alcune attività in outsourcing per conto del Titolare, nella loro qualità di responsabili esterni del trattamento, quali a titolo esemplificativo e non esaustivo, fornitori di servizi IT e cloud;\n" +
                        "dai soggetti cui la comunicazione sia obbligatoria per legge che tratteranno i suoi dati quali Titolari.\n" +
                        " \n" +
                        "\n" +
                        "4.Finalità del trattamento e base giuridica\n" +
                        "\n" +
                        "Il Titolare tratterà i suoi Dati Personali per il conseguimento di finalità precise e solo in presenza di una specifica base giuridica prevista dalla legge applicabile in materia di privacy e protezione dei dati personali. Nello specifico, i dati verranno trattati per finalità connesse alle reciproche obbligazioni derivanti dal rapporto contrattuale con Lei instaurato ed il Titolare tratterà i suoi Dati Personali solo quando ricorre una o più delle seguenti basi giuridiche:\n" +
                        "\n" +
                        "lei ha prestato il suo consenso libero, specifico, informato, inequivocabile ed espresso al trattamento tramite l’utilizzo dell’App e/o del “wearable device”;\n" +
                        "il trattamento è necessario per l’esecuzione delle obbligazioni previste dal contratto in essere con l’interessato (persona fisica/giuridica);\n" +
                        "in presenza di un legittimo interesse del Titolare;\n" +
                        "il Titolare è tenuto in forza di un obbligo di legge a trattare i Dati Personali;\n" +
                        "in esecuzione di un compito di interesse pubblico.\n" +
                        " \n" +
                        "\n" +
                        "5.Tipologia di dati e utilizzo\n" +
                        "\n" +
                        "Il Titolare riceve o raccoglie dati personali, come descritto in dettaglio di seguito, quando fornisce i propri Servizi, incluso quando si accede a, si scarica, si installa l’App, si utilizza il “wearable device”, si utilizzano i Servizi dell’Azienda. Il Titolare può utilizzare questi dati personali per prestare i Servizi richiesti dagli utenti come necessità contrattuale e per tutte le altre finalità citate al Punto 4. Qualora un utente non desideri che l’Azienda raccolga o tratti i suoi dati personali, potrebbe non essere in grado di utilizzare i Servizi. Prima di raccogliere dati personali sensibili, il Titolare informa l’utente e chiede il suo esplicito consenso. Gli utenti possono revocare il proprio consenso in qualsiasi momento, senza pregiudicare la liceità del trattamento basato sul consenso prima della revoca.\n" +
                        "\n" +
                        "I dati forniti dagli utenti comprendono:\n" +
                        "\n" +
                        "Data di nascita del bambino\n" +
                        "Nome del bambino\n" +
                        "Sesso del bambino\n" +
                        "Temperatura del bambino\n" +
                        "Rilevazione respiro\n" +
                        "Rilevazione della posizione del bambino\n" +
                        "Rilevazione del livello di rumore prossimità del bambino\n" +
                        "Tappe di sviluppo fondamentali raggiunte dal bambino\n" +
                        "Attività del bambino\n" +
                        "Dati sulla crescita del bambino\n" +
                        "Dati sul sonno del bambino\n" +
                        "Sesso dell’utente\n" +
                        "Età dell’utente\n" +
                        "Fotografia dell’utente\n" +
                        "Località di residenza dell’utente\n" +
                        "Relazione dell’utente con il bambino\n" +
                        "Fotografie caricate dall’utente\n" +
                        "Note inserite dall’utente\n" +
                        "Voci del diario e ricordi inserite dell’utente\n" +
                        "Farmaci assunti dal bambino (note personali che si desidera registrare, come: dosaggio, nome, etichetta)\n" +
                        "Vaccinazioni effettuate dal bambino (note personali che si desidera registrare, come: nome, reazione)\n" +
                        "Visite mediche del bambino (note personali che si desidera registrare, come: peso, altezza, data e ora dell’appuntamento, nome e professione del professionista sanitario)\n" +
                        "Dati sull’allattamento al seno della madre\n" +
                        "L’utente può caricare questi dati nell’App. Tali dati vengono utilizzati per fornire informazioni all’utente sullo sviluppo e sul benessere del bambino e per fornire contenuti, messaggi e consigli personalizzati, tramite l’invio di notifiche push e messaggi in-app. Si ricorda agli utenti di non inviare e di non divulgare tramite l’App altri dati personali sensibili (ad esempio: informazioni relative a origine razziale o etnica, opinioni politiche, religione o altre convinzioni, salute, biometria o caratteristiche genetiche, precedenti criminali o appartenenza sindacale).\n" +
                        "\n" +
                        "Creando un account e fornendo il proprio consenso esplicito, i dati dell’utente saranno conservati in modo sicuro.\n" +
                        "\n" +
                        "Quando un utente crea un account il Titolare raccoglie i suoi dati personali che comprendono:\n" +
                        "\n" +
                        "nome dell’utente\n" +
                        "data di nascita\n" +
                        "indirizzo e-mail\n" +
                        "password\n" +
                        "I dati personali raccolti vengono utilizzati per la creazione e la gestione degli account degli utenti. Qualora l’utente crei un account per accedere all’App, invieremo una e-mail di benvenuto e comunicheremo con l’utente in risposta alle sue richieste.\n" +
                        "\n" +
                        "L’App può richiedere l’autorizzazione dell’utente ad accedere al telefono o ai sensori (ad es.  fotocamera, Wi-Fi o Bluetooth) o ad altri dati (ad es. fotografie, agenda o contatti) sul dispositivo mobile dell’utente.\n" +
                        "\n" +
                        "Il Titolare utilizza tali dati solo qualora siano necessari per fornire i Servizi all’utente e solo dopo che l’utente avrà fornito il suo esplicito consenso.\n" +
                        "\n" +
                        "In alcuni casi, tale autorizzazione è un prerequisito tecnico dei sistemi operativi del dispositivo mobile dell’utente. In tali casi, l’App potrà chiedere l’autorizzazione ad accedere a tali sensori o dati, tuttavia L’Azienda non raccoglie tali dati, a meno che non sia necessario per fornire all’utente il Servizio offerto dall’App e solo dopo che l’utente avrà fornito il proprio consenso.\n" +
                        "\n" +
                        "L’utente può fornire informazioni relative all’utilizzo dei Servizi, e il metodo di contatto, ove richiesto, per fornire l’assistenza al cliente necessario. Il Titolare utilizzerà le informazioni fornite dall’utente per rispondere alle richieste di assistenza che perverranno.\n" +
                        "\n" +
                        " \n" +
                        "\n" +
                        "6.Modalità di trattamento\n" +
                        "\n" +
                        "Il trattamento dei suoi Dati Personali è realizzato per mezzo delle operazioni indicate all’art. 4 punto 2 del GDPR e precisamente: raccolta, registrazione, organizzazione, conservazione, consultazione, elaborazione, estrazione, utilizzo, cancellazione e distruzione dei dati. Il trattamento dei dati avverrà mediante strumenti idonei a garantire la sicurezza e la riservatezza e potrà essere effettuato anche attraverso strumenti automatizzati atti a memorizzare, gestire e trasmettere i dati stessi.\n" +
                        "\n" +
                        "La informiamo, inoltre, che i dati personali a Lei riferibili saranno:\n" +
                        "\n" +
                        "trattati in modo lecito e secondo correttezza;\n" +
                        "raccolti e registrati per scopi determinati, espliciti e legittimi ed utilizzati in altre operazioni del trattamento in termini compatibili con tali scopi;\n" +
                        "esatti e, se necessario, aggiornati;\n" +
                        "pertinenti, completi e non eccedenti rispetto alle finalità per le quali sono stati raccolti o successivamente trattati;\n" +
                        "conservati in una forma che consenta l’identificazione dell’interessato per un periodo di tempo non superiore a quello necessario agli scopi per i quali essi sono stati raccolti o successivamente trattati.\n" +
                        " \n" +
                        "\n" +
                        "7.Periodo di conservazione dei dati\n" +
                        "\n" +
                        "I Dati Personali oggetto di trattamento per le finalità di cui sopra saranno conservati nel rispetto dei principi di proporzionalità e necessità, e comunque fino a che non siano state perseguite le finalità del trattamento, finché l’account rimarrà attivo. I dati forniti verranno conservati fino a che l’utente non revocherà il consenso. Qualora l’utente smetta di utilizzare l’App, dopo tre anni il Titolare chiederà all’utente se desideri conservare i suoi dati personali, concedendo all’utente un periodo di tre mesi in cui potrà acconsentire alla conservazione. Se l’Azienda non riceverà il consenso da parte dell’utente dopo questo contatto, i dati personali dell’utente verranno definitivamente cancellati.\n" +
                        "\n" +
                        "Pertanto i Dati Personali saranno conservati di norma fintanto che sussista il rapporto negoziale con il Titolare, fatta salva la gestione dei dati in ipotesi di eventuali contestazioni o reclami, quali ad esempio quelle aventi ad oggetto l’adempimento delle prestazioni richieste, e l’eventuale conservazione degli stessi, laddove necessaria, ai fini della gestione di azioni giudiziali in corso e all’adempimento di specifici obblighi di legge. In ogni caso l’Azienda pratica regole che impediscono la conservazione dei dati a tempo indeterminato e limita quindi il tempo di conservazione nel rispetto del principio di minimizzazione del trattamento dei dati.\n" +
                        "\n" +
                        " \n" +
                        "\n" +
                        "8.Trasferimento dei dati\n" +
                        "\n" +
                        "I dati personali sono conservati su archivi elettronici ubicati in Italia. In qualsiasi caso i dati personali non saranno mai diffusi.\n" +
                        "\n" +
                        " \n" +
                        "\n" +
                        "9.Natura del conferimento dei dati e conseguenze del rifiuto di rispondere\n" +
                        "\n" +
                        "Fermo restando, che Lei ha il diritto di opporsi, in tutto o in parte:\n" +
                        "\n" +
                        "per motivi legittimi al trattamento dei dati personali che la riguardano, ancorché pertinenti allo scopo della raccolta;\n" +
                        "si evidenzia che il conferimento dei dati per le finalità di cui all’art. 4 è obbligatorio ed in loro assenza, non potremo realizzare l’attività richiesta. All’uopo si specifica che l’acquisizione dei dati che Le sono stati o Le potranno essere richiesti è presupposto indispensabile per l’evasione delle attività richieste e che l’eventuale rifiuto di fornire i dati a Lei richiesti al momento della raccolta, può comportare l’oggettiva impossibilità per la scrivente di instaurare o di condurre regolarmente con Lei un rapporto contrattuale.\n" +
                        "\n" +
                        " \n" +
                        "\n" +
                        "10.Diritti dell’interessato\n" +
                        "\n" +
                        "In relazione ai predetti trattamenti, Lei potrà esercitare i diritti di cui agli artt. 15-21 GDPR (Diritto di accesso, diritto di rettifica, diritto alla cancellazione, diritto di limitazione di trattamento, diritto alla portabilità dei dati, diritto di opposizione) nei confronti del Titolare mediante richiesta rivolta senza formalità anche per il tramite di un incaricato, alla quale sarà fornito idoneo riscontro senza ritardo; La richiesta rivolta può essere trasmessa anche mediante lettera raccomandata o posta elettronica ai recapiti sopra indicati.\n" +
                        "\n" +
                        "Lei ha inoltre il diritto di reclamo all’Autorità Garante tramite raccomandata A/R indirizzata a Garante per la protezione dei dati personali, Piazza Venezia, 11, 00187 Roma, e-mail all’indirizzo: protocollo@gpdp.it oppure protocollo@pec.gpdp.it o fax al numero: 06/69677.3785.");

                materialAlertDialogBuilder.setPositiveButton("Accetto", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        startActivity(new Intent(Login.this, CreateUser.class));

                    }
                });
                materialAlertDialogBuilder.setNegativeButton("Rifiuto", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        //checkBox.setChecked(false);
                    }
                });
                materialAlertDialogBuilder.show();


                //TERMINI E CONDIZIONI

            }

        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                perforLogin();
            }
        });
    }

    private void perforLogin() {
        String email=inputEmail.getText().toString();
        String password=inputPassword.getText().toString();

        if(!email.matches(emailPattern)){
            inputEmail.setError("Enter correct email");
        }else if(password.isEmpty() || password.length()<6){
            inputPassword.setError("Enter propper password");
        }else {
            fAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(Login.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                    sendUsertoNextActivity();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Login.this, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


        }
    }

    private void sendUsertoNextActivity() {
        SaveSharedPreference.setUserName(Login.this, inputEmail.getText().toString());
        Intent intent = new Intent(Login.this, Bacheca.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


}