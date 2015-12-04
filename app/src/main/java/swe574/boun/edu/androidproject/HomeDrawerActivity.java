package swe574.boun.edu.androidproject;

public class HomeDpCompatActivids AprawerActivity extenty

        implements NavigationView.OnNavigationItemSelectedListener{
private User mUser;
        int code=0;

@Override
protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer=(DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(
        this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView=(NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if(savedInstanceState==null){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeNavigationFragment()).commit();
        }

        mUser=getIntent().getParcelableExtra("user");
        }

@Override
public void onBackPressed(){
        DrawerLayout drawer=(DrawerLayout)findViewById(R.id.drawer_layout);
        if(drawer.isDrawerOpen(GravityCompat.START)){
        drawer.closeDrawer(GravityCompat.START);
        }else{
        super.onBackPressed();
        }
        }

@Override
public boolean onCreateOptionsMenu(Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
        }

@Override
public boolean onOptionsItemSelected(MenuItem item){
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id=item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id==R.id.action_settings){
        return true;
        }

        return super.onOptionsItemSelected(item);
        }

@Override
public boolean onNavigationItemSelected(MenuItem item){
        // Handle navigation view item clicks here.
        int id=item.getItemId();
        HomeFragment fragment=null;

        switch(id){
        case R.id.nav_home:
        fragment=HomeNavigationFragment.newInstance(mUser);
        break;
        case R.id.nav_groups:
        fragment=GroupsNavigationFragment.newInstance(mUser);
        break;
        case R.id.nav_profile:
        fragment=ProfileNavigationFragment.newInstance(mUser);
        break;
        case R.id.nav_messages:
        fragment=MessageNavigationFragment.newInstance(mUser);
        break;
        }

        FragmentManager manager=getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container,fragment).addToBackStack("fragment"+code++).commit();
        DrawerLayout drawer=(DrawerLayout)findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
        }

        }
