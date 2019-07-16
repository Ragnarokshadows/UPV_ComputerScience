flight(barcelona,madrid,
       date(10,nov,2030),time(13,05),
       date(10,nov,2030),time(15,05),
       120,80).
flight(barcelona,valencia,
       date(10,nov,2030),time(13,05),
       date(10,nov,2030),time(15,05),
       120,20).
flight(madrid,london,
       date(10,nov,2030),time(16,05),
       date(10,nov,2030),time(17,35),
       90,140).
flight(valencia,london,
       date(10,nov,2030),time(16,05),
       date(10,nov,2030),time(17,35),
       90,50).
flight(madrid,london,
       date(10,nov,2030),time(23,05),
       date(11,nov,2030),time(00,25),
       80,50).

connection_same_day(Origin,Destination,Date):- 
    flight(Origin,Connection,Date,_,Date,time(Hl1,Ms1),_,_),
    flight(Connection,Destination,Date,time(Hs2,Ms2),Date,_,_,_),
    Hl1_in_minutes is Hl1 * 60 + Ms1 + 60,
    Hs2_in_minutes is Hs2 * 60 + Ms2,
    Hl1_in_minutes =< Hs2_in_minutes.
