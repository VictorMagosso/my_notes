//activity responsável por criar as anotações. Aqui você vai escolher o título que é o que vamos recuperar no Adapter
//Ao clicar na nota com o titulo, vamos conseguir ver tudo o que escrever aqui
public class AdicionarNotes extends AppCompatActivity {

    private EditText editNotes, editTitle;
    private FloatingActionButton fabSalvarNote;
    private Notes noteAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adicionar_notes );
        editNotes = findViewById(R.id.editNotes);
        editTitle = findViewById( R.id.editTitle );
        fabSalvarNote = findViewById( R.id.fabSalvarNote );


        //Recuperar tarefa, caso seja edição da anotação
        noteAtual = (Notes) getIntent().getSerializableExtra("noteSelecionado");

        //Configurar caixa de texto
        if (noteAtual != null) {
            editTitle.setText(noteAtual.getTitle());
            editNotes.setText(noteAtual.getNote());
        }

    fabSalvarNote.setOnClickListener( new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            NotesDAO notesDAO = new NotesDAO(getApplicationContext());

            if (noteAtual != null) {//edição

                String nomeNote = editTitle.getText().toString();
                String descNote = editNotes.getText().toString();
                if (!nomeNote.isEmpty() || !descNote.isEmpty()) {

                    Notes notes = new Notes();
                    notes.setTitle(nomeNote);
                    notes.setNote(descNote);
                    notes.setId(noteAtual.getId());

                    //atualizar no banco de dados
                    if (notesDAO.atualizar((notes))){
                        finish();//o método finish, faz retornar a tela
                        Toast.makeText(getApplicationContext(), "Success saving your note!", Toast.LENGTH_LONG).show();

                    }else {
                        Toast.makeText(getApplicationContext(), "Error saving your note", Toast.LENGTH_LONG).show();
                    }
                }

            } else {

                String nomeNote = editTitle.getText().toString();
                String descNote = editNotes.getText().toString();
                if (!nomeNote.isEmpty() || !descNote.isEmpty()) {
                    Notes notes = new Notes();
                    notes.setTitle(nomeNote);
                    notes.setNote(descNote);


                    if (notesDAO.salvar(notes)) {
                        finish();//o método finish, faz retornar a tela
                        Toast.makeText(getApplicationContext(), "Success saving your note!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Error saving your note", Toast.LENGTH_LONG).show();
                    }

                }

            }
        }
    } );

  }
}



