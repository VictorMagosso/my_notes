
public class NotesActivity extends AppCompatActivity {
    RecyclerView recyclerNotes;
    Notes noteSelecionado;
    List<Notes> listaNotes = new ArrayList<>(  );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.my_notes );
        getSupportActionBar().hide();
        recyclerNotes = findViewById( R.id.recyclerNotes ); //recyclerview dentro do layout, normal
        
        //utilização de sqlite como banco de dados
        NotesDbHelper db = new NotesDbHelper(getApplicationContext());
        
        db.getReadableDatabase().insert("tarefas",null,cv);

        //após chamar esse metodo, ele vai sugerir criar a classe RecyclerItemClickListener. Pode criar conforme sugestão do programa
        recyclerNotes.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerNotes,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Notes selecionada = listaNotes.get(position); //pega a posição do "toque" na tela, representa o item do recycler
                          //aqui vamos chamar uma activity que possui os detalhes da nota. Nós salvamos ela no adapter só recuperando o titulo, mas queremos ver ela inteira
                                Intent intent = new Intent(NotesActivity.this, DetalheNoteActivity.class);
                                intent.putExtra("tarefaSelecionada",selecionada);
                                intent.putExtra("titulo",selecionada.getTitle());
                                intent.putExtra("note",selecionada.getNote());

                                startActivity(intent);
                            }
                      //para excluir a anotação desejada, mesmo raciocíonio dos metodos acima
                            @Override
                            public void onLongItemClick(View view, int position) {
                                noteSelecionado = listaNotes.get(position);
                                AlertDialog.Builder dialog = new AlertDialog.Builder(NotesActivity.this);

                                dialog.setTitle("Configurar exclusão");
                                dialog.setMessage("Deseja realmente excluir a anotação \"" + noteSelecionado.getTitle() + "\"?");

                                dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        NotesDAO notesDAO = new NotesDAO(getApplicationContext());
                                        if(notesDAO.deletar(noteSelecionado)){

                                            carregarListaNotes();
                                            Toast.makeText(getApplicationContext(), "Note successfully excluded!", Toast.LENGTH_LONG).show();

                                        }else{
                                            Toast.makeText(getApplicationContext(), "Error excluding the note", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                                dialog.setNegativeButton("Não", null);

                                dialog.create();
                                dialog.show();

                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );

        FloatingActionButton fab = findViewById(R.id.fabAdicionarNote);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), AdicionarNotes.class);
                startActivity(intent);

            }
        });
    }

    public void carregarListaNotes(){

        NotesDAO notesDAO = new NotesDAO(getApplicationContext());
        listaNotes = notesDAO.listar();


        AdapterNotes adapter = new AdapterNotes(listaNotes, getApplicationContext());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerNotes.setLayoutManager(layoutManager);
        recyclerNotes.setHasFixedSize(true);
        recyclerNotes.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        recyclerNotes.setAdapter(adapter);
  }
    @Override
    protected void onStart() {
        carregarListaNotes();
         super.onStart();
    }

}
