
public class Ambulance{
    public Ambulance(int re, String eq, double la, double lo, EmergenyCallService ecs){
        registration_number = re;
        equipment_type = eq;
        latitude = la;
        longitude = lo;
        emergencyCallService = ecs;
    }
    public int registration_number{
        get;
        set;
    }
    public String equipment_type{
        get;
        set;
    }
    public double latitude{
        get;
        set;
    }
    public double longitude{
        get;
        set;
    }
    public EmergenyCallService emergencyCallService{
        get;
        set;
    }
    public EmergenyCall emergencyCall{
        get;
        set;
    }
}

public class Private: Ambulance{
    public String company_name{
        get;
        set;
    }
    public Private(String cn, int re, String eq, double la, double lo, EmergenyCallService ecs){
        super(re, eq, la, lo, ecs);
        company_name = cn;
    }
}

public class HospitalBased: Ambulance{
    public Hospital hospital{
        get;
        set;
    }
    public HospitalBased(int re, String eq, double la, double lo, EmergenyCallService ecs, Hospital h){
        super(re, eq, la, lo, ecs);
        hospital = h;
    }
}

public class EmergenyCallService{
    public ICollection<Ambulance> ambulances{
        get;
        set;
    }
    public ICollection<EmergenyCall> calls{
        get;
        set;
    }
    public ICollection<Hospital> hospitals{
        get;
        set;
    }
    public EmergenyCallService(Hospital hospital, Ambulance ambulance){
        ambulances = new List<Ambulance>();
        ambulances.Add(ambulance);

        hospitals = new List<Hospital>();
        hospitals.Add(hospital);

        calls = new List<EmergenyCall>();
    }    
}

public class Hospital{
    public String name{
        get;
        set;
    }
    public String address{
        get;
        set;
    }
    public double latitude{
        get;
        set;
    }
    public double longitude{
        get;
        set;
    }
    public EmergenyCallService emergencyCallService{
        get;
        set;
    }
    public ICollection<HospitalBased> hospitalBaseds{
        get;
        set;
    }
    public ICollection<EmergenyCall> emergencyCalls{
        get;
        set;
    }
    public ICollection<Deployment> deployments{
        get;
        set;
    }
    public Hospital(Deployment d, EmergenyCallService ecs, String n, String a, double la, double lo){
        name = n;
        address = a;
        latitude = la;
        longitude = lo;

        deployments = new List<Deployment>();
        deployments.Add(d);

        hospitalBaseds = new List<HospitalBased>();

        emergencyCalls = new List<EmergenyCall>();

        emergencyCallService = ecs;
    } 
}

public class Deployment{
    public int floor{
        get;
        set;
    }
    public int num_employees{
        get;
        set;
    }
    public Hospital hospital{
        get;
        set;
    }
    public Speciality speciality{
        get;
        set;
    }
    public Deployment(Hospital h, Speciality s, int f, int n){
        floor = f;
        num_employees = n;
        hospital = h;
        speciality = s;
    } 
}

public class Speciality{
    public ICollection<Deployment> deployments{
        get;
        set;
    }
    public ICollection<Symptom> symptoms{
        get;
        set;
    }
    public String name{
        get;
        set;
    }
    public Speciality(Deployment d, String n){
        deployments = new List<Deployment>();
        deployments.Add(d);

        symptoms = new List<Symptom>();

        name = n;
    } 
}

public class Symptom{
    public ICollection<Speciality> specialities{
        get;
        set;
    }
    public ICollection<EmergenyCall> emergencyCalls{
        get;
        set;
    }
    public String stage_of_gravity{
        get;
        set;
    }
    public double duration{
        get;
        set;
    }
    public String description{
        get;
        set;
    }
    public Symptom(String s, double d, String des, EmergenyCall e, Speciality s){
        specialities = new List<Speciality>();
        specialities.Add(s);

        emergencyCalls = new List<EmergenyCall>();
        emergencyCalls.Add(e);

        stage_of_gravity = s;
        duration = d;
        description = des;
    } 
}

public class EmergenyCall{
    public ICollection<Symptom> symptoms{
        get;
        set;
    }
    public Ambulance ambulance{
        get;
        set;
    }
    public Patient patient{
        get;
        set;
    }
    public Hospital hospital{
        get;
        set;
    }
    public EmergenyCallService emergencyCallService{
        get;
        set;
    }
    public double latitude{
        get;
        set;
    }
    public double longitude{
        get;
        set;
    }
    public Date date{
        get;
        set;
    }
    public String time{
        get;
        set;
    }
    public EmergenyCall(double la, double lo, Date d, String t, EmergenyCallService ecs, Patient p, Symptom sym){
        symptoms = new List<Symptom>();
        symptoms.Add(sym);

        emergencyCallService = ecs;
        patient = p;
        time = t;
        date = d;
        longitude = lo;
        latitude = la;
    } 
}

public class Patient{
    public ICollection<EmergenyCall> emergencyCalls{
        get;
        set;
    }
    public String dni{
        get;
        set;
    }
    public String name{
        get;
        set;
    }
    public String surname{
        get;
        set;
    }
    public String sex{
        get;
        set;
    }
    public int age{
        get;
        set;
    }
    public int phone_number{
        get;
        set;
    }
    public String address{
        get;
        set;
    }
    public Patient(String d, String n, String sur, String s, int a, int p, String add, EmergenyCall ec){
        emergencyCalls = new List<EmergenyCall>();
        emergencyCalls.Add(ec);

        dni = d;
        name = n;
        surname = sur;
        sex = s;
        age = a;
        phone_number = p;
        address = add;
    } 
}