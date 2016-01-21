
//Korrekturfaktoren
// Untermaß Durchmesser kleine Löcher
um_small_hole = -0.4;

// Correction for imperfect printing
// the inner diameter is 49.5 instead of 50.0 after print with PLA_13, so add 0.5 mm
//    (10.01.2016 ad 0.6, because 49.9 was measured)
dadd_inner_tube = 0.6;

dbaseplate = 9.9;
xwidth_base_plate = 90;
zwidth_base_plate = 80;
faces_of_small_holes = 12;
// The top holes lay 20.5 mm above zero
ztop_holes = 20.5;
// The bottom holes lay 17.5 mm below zero
zbottom_holes = -17.5;
// The left holes lay 22.2 mm left of zero in X-Direction
xleft_holes = -22.2;
// The right holes lay 26.8 mm right of zero in X-Direction
xright_holes = 26.8;

// The dimensions of central hole
ztop_central_hole = 13;
zbottom_central_hole = -15;
xleft_central_hole = -30;
xright_central_hole = 17;

// Holes to mount all plates together
// The top holes lay 20.5 mm above zero
ztop_holes_2 = ( zwidth_base_plate / 2 ) - 5;
// The bottom holes lay 17.5 mm below zero
zbottom_holes_2 = - ( zwidth_base_plate / 2 ) + 5;
// The left holes lay 22.2 mm left of zero in X-Direction
xleft_holes_2 = - ( xwidth_base_plate / 2) + 5;
// The right holes lay 26.8 mm right of zero in X-Direction
xright_holes_2 = ( xwidth_base_plate / 2) - 5;

// ------------- Central_plate (To mount the sensor) ------------------

difference(){
// Main Plate for sensor
translate([-xwidth_base_plate/2,0,-zwidth_base_plate/2])
cube([xwidth_base_plate,dbaseplate,zwidth_base_plate],center = false);
// Holes to mount sensor
// bottom left    
translate([xleft_holes,dbaseplate,zbottom_holes])
rotate([90,0,0])
cylinder(h=dbaseplate, d = 3.2 - um_small_hole, $fn = faces_of_small_holes );    
// bottom right    
translate([xright_holes,dbaseplate,zbottom_holes])
rotate([90,0,0])
cylinder(h=dbaseplate, d = 3.2 - um_small_hole, $fn = faces_of_small_holes );  
// top left    
translate([xleft_holes,dbaseplate,ztop_holes])
rotate([90,0,0])
cylinder(h=dbaseplate, d = 3.2 - um_small_hole, $fn = faces_of_small_holes );  
// top right    
translate([xright_holes,dbaseplate,ztop_holes])
rotate([90,0,0])
cylinder(h=dbaseplate, d = 3.2 - um_small_hole, $fn = faces_of_small_holes );
    
// Central hole in main plate for sensor for electronics
translate([xleft_central_hole,0,zbottom_central_hole])
cube([xright_central_hole-xleft_central_hole,dbaseplate,ztop_central_hole-zbottom_central_hole]);

// Holes to fix plates
// bottom left    
translate([xleft_holes_2,dbaseplate,zbottom_holes_2])
rotate([90,0,0])
cylinder(h=dbaseplate, d = 4.2 - um_small_hole, $fn = faces_of_small_holes );    
// bottom right    
translate([xright_holes_2,dbaseplate,zbottom_holes_2])
rotate([90,0,0])
cylinder(h=dbaseplate, d = 4.2 - um_small_hole, $fn = faces_of_small_holes );  
// top left    
translate([xleft_holes_2,dbaseplate,ztop_holes_2])
rotate([90,0,0])
cylinder(h=dbaseplate, d = 4.2 - um_small_hole, $fn = faces_of_small_holes );  
// top right    
translate([xright_holes_2,dbaseplate,ztop_holes_2])
rotate([90,0,0])
cylinder(h=dbaseplate, d = 4.2 - um_small_hole, $fn = faces_of_small_holes );

// Lines for measurements
// left    
translate([-xwidth_base_plate/2,dbaseplate,0])
rotate([90,0,0])
cylinder(h=dbaseplate, d = 1, $fn = faces_of_small_holes );    
// right    
translate([xwidth_base_plate/2,dbaseplate,0])
rotate([90,0,0])
cylinder(h=dbaseplate, d = 1, $fn = faces_of_small_holes );  
// top    
translate([0,dbaseplate,zwidth_base_plate/2])
rotate([90,0,0])
cylinder(h=dbaseplate, d = 1, $fn = faces_of_small_holes );  
// bottom    
translate([0,dbaseplate,-zwidth_base_plate/2])
rotate([90,0,0])
cylinder(h=dbaseplate, d = 1, $fn = faces_of_small_holes );
};


// ---------------------- Backplate -------------------------------
// Distance while constructing
yconstruct_backplate = 20;
// Dimensions
dbackplate = 12;
ydepth_screw_head = 4;

// Hole for Cable
ztop_hole_for_cable = 5;
zbottom_hole_for_cable = -9;
xleft_hole_for_cable = 12;
xright_hole_for_cable = xwidth_base_plate/2;
ydeep_for_cable = 1.2;
// Hole2 for Cable
ztop_hole2_for_cable = 5;
zbottom_hole2_for_cable = -9;
xleft_hole2_for_cable = xwidth_base_plate/2-8;
xright_hole2_for_cable = xwidth_base_plate/2;
ydeep2_for_cable = dbackplate;
// Depress Cable
ztop_depress_cable = 12;
zbottom_depress_cable = -14;
xleft_depress_cable = -3;
xright_depress_cable = 11;
yheight_depress_cable = 3;
// Hole for Plug
ztop_hole_plug = 8;
zbottom_hole_plug = -13;
xleft_hole_plug = -28;
xright_hole_plug = -13;
ydeep_plug = 9;

translate([0,dbaseplate + yconstruct_backplate,0])
union(){
difference(){
// Back Plate for sensor
translate([-xwidth_base_plate/2,0,-zwidth_base_plate/2])
cube([xwidth_base_plate,dbackplate,zwidth_base_plate],center = false);
    
// Holes to fix plates
// bottom left    
translate([xleft_holes_2,dbackplate,zbottom_holes_2])
rotate([90,0,0])
cylinder(h=dbackplate, d = 4.2 - um_small_hole, $fn = faces_of_small_holes );    
// bottom right    
translate([xright_holes_2,dbackplate,zbottom_holes_2])
rotate([90,0,0])
cylinder(h=dbackplate, d = 4.2 - um_small_hole, $fn = faces_of_small_holes );  
// top left    
translate([xleft_holes_2,dbackplate,ztop_holes_2])
rotate([90,0,0])
cylinder(h=dbackplate, d = 4.2 - um_small_hole, $fn = faces_of_small_holes );  
// top right    
translate([xright_holes_2,dbackplate,ztop_holes_2])
rotate([90,0,0])
cylinder(h=dbackplate, d = 4.2 - um_small_hole, $fn = faces_of_small_holes );    
    
// Lines for measurements
// left    
translate([-xwidth_base_plate/2,dbackplate,0])
rotate([90,0,0])
cylinder(h=dbackplate, d = 1, $fn = faces_of_small_holes );    
// right    
translate([xwidth_base_plate/2,dbackplate,0])
rotate([90,0,0])
cylinder(h=dbackplate, d = 1, $fn = faces_of_small_holes );  
// top    
translate([0,dbackplate,zwidth_base_plate/2])
rotate([90,0,0])
cylinder(h=dbackplate, d = 1, $fn = faces_of_small_holes );  
// bottom    
translate([0,dbackplate,-zwidth_base_plate/2])
rotate([90,0,0])
cylinder(h=dbackplate, d = 1, $fn = faces_of_small_holes );
 
// Holes for screw heads to mount sensor
// bottom left    
translate([xleft_holes,ydepth_screw_head,zbottom_holes])
rotate([90,0,0])
cylinder(h=ydepth_screw_head, d = 8 - um_small_hole, $fn = faces_of_small_holes );    
// bottom right    
translate([xright_holes,ydepth_screw_head,zbottom_holes])
rotate([90,0,0])
cylinder(h=ydepth_screw_head, d = 8 - um_small_hole, $fn = faces_of_small_holes );  
// top left    
translate([xleft_holes,ydepth_screw_head,ztop_holes])
rotate([90,0,0])
cylinder(h=ydepth_screw_head, d = 8 - um_small_hole, $fn = faces_of_small_holes );  
// top right    
translate([xright_holes,ydepth_screw_head,ztop_holes])
rotate([90,0,0])
cylinder(h=ydepth_screw_head, d = 8 - um_small_hole, $fn = faces_of_small_holes ); 
 
// Hole for Plug in backplate
translate([xleft_hole_plug,0,zbottom_hole_plug])
cube([xright_hole_plug-xleft_hole_plug,ydeep_plug,ztop_hole_plug-zbottom_hole_plug]);
// Hole for Cable in backplate
translate([xleft_hole_for_cable,0,zbottom_hole_for_cable])
cube([xright_hole_for_cable-xleft_hole_for_cable,ydeep_for_cable,ztop_hole_for_cable-zbottom_hole_for_cable]);
// Hole2 for Cable in backplate
translate([xleft_hole2_for_cable,0,zbottom_hole2_for_cable])
cube([xright_hole2_for_cable-xleft_hole2_for_cable,ydeep2_for_cable,ztop_hole2_for_cable-zbottom_hole2_for_cable]); 
};
// Depress Cable
translate([xleft_depress_cable,-yheight_depress_cable,zbottom_depress_cable])
cube([xright_depress_cable-xleft_depress_cable,yheight_depress_cable,ztop_depress_cable-zbottom_depress_cable]);
};

// ------------------------ Front of camera ------------------------------

// Distance while constructing
yconstruct_front = 0;
// Dimensions
dfront_plate = 12;

// Front Plate is wider
front_plate_wider = 20;
xwidth_front_plate = xwidth_base_plate + front_plate_wider;
zwidth_front_plate = zwidth_base_plate + front_plate_wider;

// Empty Space in Middle

ztop_hole_front = zwidth_base_plate/2 - 10;
zbottom_hole_front = -zwidth_base_plate/2 +10;
xleft_hole_front = -xwidth_base_plate/2 +10 ;
xright_hole_front = xwidth_base_plate/2 -10;

// Shielding
shield_height = 20;

translate([0,- dfront_plate + yconstruct_front,0])
union(){
// Cube at bottom so that camera does not drop
translate([-5,-110,-50])
cube([10,110,10]);    
// Shielding over sensor and backplate    
difference(){  
 translate([-xwidth_front_plate/2,dfront_plate,-zwidth_front_plate/2])
cube([xwidth_front_plate,shield_height,zwidth_front_plate],center = false);  
 translate([-xwidth_base_plate/2,dfront_plate,-zwidth_base_plate/2])
cube([xwidth_base_plate,shield_height,zwidth_base_plate],center = false); 

// Lines for measurements at shielding
// left    
translate([-xwidth_front_plate/2,shield_height+dfront_plate,0])
rotate([90,0,0])
cylinder(h=shield_height, d = 1, $fn = faces_of_small_holes );    
// right    
translate([xwidth_front_plate/2,shield_height+dfront_plate,0])
rotate([90,0,0])
cylinder(h=shield_height, d = 1, $fn = faces_of_small_holes );  
// top    
translate([0,shield_height+dfront_plate,zwidth_front_plate/2])
rotate([90,0,0])
cylinder(h=shield_height, d = 1, $fn = faces_of_small_holes );  
// bottom    
translate([0,shield_height+dfront_plate,-zwidth_front_plate/2])
rotate([90,0,0])
cylinder(h=shield_height, d = 1, $fn = faces_of_small_holes ); 
    
};
difference(){
// Front lower plate
translate([-xwidth_front_plate/2,0,-zwidth_front_plate/2])
cube([xwidth_front_plate,dfront_plate,zwidth_front_plate],center = false);
    
// Holes to fix plates
// bottom left    
translate([xleft_holes_2,dfront_plate,zbottom_holes_2])
rotate([90,0,0])
cylinder(h=dfront_plate, d = 4.2 - um_small_hole, $fn = faces_of_small_holes );    
// bottom right    
translate([xright_holes_2,dfront_plate,zbottom_holes_2])
rotate([90,0,0])
cylinder(h=dfront_plate, d = 4.2 - um_small_hole, $fn = faces_of_small_holes );  
// top left    
translate([xleft_holes_2,dfront_plate,ztop_holes_2])
rotate([90,0,0])
cylinder(h=dfront_plate, d = 4.2 - um_small_hole, $fn = faces_of_small_holes );  
// top right    
translate([xright_holes_2,dfront_plate,ztop_holes_2])
rotate([90,0,0])
cylinder(h=dfront_plate, d = 4.2 - um_small_hole, $fn = faces_of_small_holes );      
   
// Lines for measurements
// left    
translate([-xwidth_front_plate/2,dfront_plate,0])
rotate([90,0,0])
cylinder(h=dfront_plate, d = 1, $fn = faces_of_small_holes );    
// right    
translate([xwidth_front_plate/2,dfront_plate,0])
rotate([90,0,0])
cylinder(h=dfront_plate, d = 1, $fn = faces_of_small_holes );  
// top    
translate([0,dfront_plate,zwidth_front_plate/2])
rotate([90,0,0])
cylinder(h=dfront_plate, d = 1, $fn = faces_of_small_holes );  
// bottom    
translate([0,dfront_plate,-zwidth_front_plate/2])
rotate([90,0,0])
cylinder(h=dfront_plate, d = 1, $fn = faces_of_small_holes ); 

// Empty Space in Middle

translate([xleft_hole_front,0,zbottom_hole_front])
cube([xright_hole_front-xleft_hole_front,dfront_plate,ztop_hole_front-zbottom_hole_front]);



};
// Tube at the front
ht1 = 5;
da1 = 99;
dt1 = 15;
ht2 = 5;
da2 = 95;
dt2 = 15;
ht3 = 5;
da3 = 90;
dt3 = 15;
ht4 = 5;
da4 = 85;
dt4 = 15;
ht5 = 5;
da5 = 80;
dt5 = 15;
ht6 = 5;
da6 = 75;
dt6 = 15;
ht7 = 5;
da7 = 70;
dt7 = 15;
ht8 = 5;
da8 = 65;
dt8 = 15;
faces_of_tube = 80;
translate([0,0,0])
rotate([90,0,0])
difference(){
  cylinder(h=ht1, d = da1, $fn = faces_of_tube);  
  cylinder(h=ht1, d = da1-dt1, $fn = faces_of_tube);   
};

translate([0,-ht1,0])
rotate([90,0,0])
difference(){
  cylinder(h=ht2, d = da2, $fn = faces_of_tube);  
  cylinder(h=ht2, d = da2-dt2, $fn = faces_of_tube);   
};

translate([0,-ht1 -ht2,0])
rotate([90,0,0])
difference(){
  cylinder(h=ht3, d = da3, $fn = faces_of_tube);  
  cylinder(h=ht3, d = da3-dt3, $fn = faces_of_tube);   
};

translate([0,-ht1 -ht2 -ht3,0])
rotate([90,0,0])
difference(){
  cylinder(h=ht4, d = da4, $fn = faces_of_tube);  
  cylinder(h=ht4, d = da4-dt4, $fn = faces_of_tube);   
};

translate([0,-ht1 -ht2 -ht3 -ht4,0])
rotate([90,0,0])
difference(){
  cylinder(h=ht5, d = da5, $fn = faces_of_tube);  
  cylinder(h=ht5, d = da5-dt5, $fn = faces_of_tube);   
};

translate([0,-ht1 -ht2 -ht3 -ht4 -ht5,0])
rotate([90,0,0])
difference(){
  cylinder(h=ht6, d = da6, $fn = faces_of_tube);  
  cylinder(h=ht6, d = da6-dt6, $fn = faces_of_tube);   
};

translate([0,-ht1 -ht2 -ht3 -ht4 -ht5 -ht6,0])
rotate([90,0,0])
difference(){
  cylinder(h=ht7, d = da7, $fn = faces_of_tube);  
  cylinder(h=ht7, d = da7-dt7, $fn = faces_of_tube);   
};

translate([0,-ht1 -ht2 -ht3 -ht4 -ht5 -ht6 -ht7,0])
rotate([90,0,0])
difference(){
  cylinder(h=ht8, d = da8, $fn = faces_of_tube);  
  cylinder(h=ht8, d = da8-dt8, $fn = faces_of_tube);   
};

translate([0,-40,0])
rotate([90,0,0])
difference(){
  cylinder(h=100, d = 60, $fn = faces_of_tube);  
  cylinder(h=100, d = 50, $fn = faces_of_tube);   
};



};


// ------------------------ Lense holder ------------------------------



// Distance while constructing
// -50 ganz eingeschoben
// -80 ca unendlich fokussiert
// -120 ca Fokus 1 Meter eingestellt
ylense_holder = -120;
xlense_holder = -100;
ylength_of_main_tube = 110;
dmain_tube_outer = 70;
dmain_tube_inner = 60 + dadd_inner_tube;
// Do not adjust a radius used for outside with correction
// value for inner tube:
dmain_tube_inner_outside = 60;

// Diameter for Lense
dlense = 51.2;

// Correct Positions of Mutter
dMutter_lense = dlense -50 + dadd_inner_tube -0.7;
dMutter_tube = dadd_inner_tube -0.2;
faces_of_tube_lense_holder = 80;
translate([xlense_holder,ylense_holder,0])
difference(){
union(){
    
// Make tube thicker for screw
translate([26.9,-125,0])
rotate([0,90,0])
translate([0,0,4])
linear_extrude(height = 8, center = true)
circle(d=15,$fn=40);

// Make tube thicker for screw
translate([-26.9,-125,0])
rotate([0,-90,0])
translate([0,0,4])
linear_extrude(height = 8, center = true)
circle(d=15,$fn=40);
   
// Make tube thicker for screw
translate([26.9,-20,0])
rotate([0,90,0])
translate([0,0,9])
linear_extrude(height = 8, center = true)
circle(d=15,$fn=40);

// Make tube thicker for screw
translate([-26.9,-20,0])
rotate([0,-90,0])
translate([0,0,9])
linear_extrude(height = 8, center = true)
circle(d=15,$fn=40);    
    
    

    
// Main Tube    
rotate([90,0,0])
difference(){
  cylinder(h=ylength_of_main_tube, d = dmain_tube_outer, $fn = faces_of_tube_lense_holder);  
  cylinder(h=ylength_of_main_tube, d = dmain_tube_inner, $fn = faces_of_tube_lense_holder); 
};
// Base for Lense
translate([0,-ylength_of_main_tube+10,0])
rotate([90,0,0])
difference(){
  cylinder(h=10, d = dmain_tube_inner, $fn = faces_of_tube_lense_holder);  
  cylinder(h=10, d = 45, $fn = faces_of_tube_lense_holder); 
};
// Ring to guide lense and to mount further structures
translate([0,-ylength_of_main_tube,0])
rotate([90,0,0])
difference(){
  cylinder(h=40, d = dmain_tube_inner_outside, $fn = faces_of_tube_lense_holder);  
  cylinder(h=40, d = dlense + dadd_inner_tube, $fn = faces_of_tube_lense_holder); 
};
// Structure to mount further devices

// Cube at bottom so that camera does not drop
translate([-5,-120,-50])
cube([10,45,10]); 
// Fix Cube to tube
translate([-5,-120,-40])
cube([10,10,11]); 
translate([-5,-85,-40])
cube([10,10,8]); 

// Mutter positive




};

// Mutter negative

// Place for "Mutter"
translate([26.9 + dMutter_lense ,-125,0])
rotate([0,90,0])
union(){
linear_extrude(height = 4.8, center = true)
circle(d=9.2,$fn=6);
translate([0,0,8])
linear_extrude(height = 15, center = true)
circle(d=5.2,$fn=40);
}; 

// Place for "Mutter"
translate([-26.9 - dMutter_lense,-125,0])
rotate([0,-90,0])
union(){
linear_extrude(height = 4.8, center = true)
circle(d=9.2,$fn=6);
translate([0,0,8])
linear_extrude(height = 15, center = true)
circle(d=5.2,$fn=40);
}; 

// 2.    
    
// Place for "Mutter"
translate([31.9 + dMutter_tube,-20,0])
rotate([0,90,0])
union(){
linear_extrude(height = 4.8, center = true)
circle(d=9.2,$fn=6);
translate([0,0,8])
linear_extrude(height = 15, center = true)
circle(d=5.2,$fn=40);
}; 

// Place for "Mutter"
translate([-31.9 -dMutter_tube,-20,0])
rotate([0,-90,0])
union(){
linear_extrude(height = 4.8, center = true)
circle(d=9.2,$fn=6);
translate([0,0,8])
linear_extrude(height = 15, center = true)
circle(d=5.2,$fn=40);
}; 

};


// ------------------------ Ring to fix lense ------------------------------
xfix_lense = -20;
translate([xlense_holder+xfix_lense,ylense_holder,0])
union(){
 translate([0,-ylength_of_main_tube-10,0])
rotate([90,0,0])
difference(){
  cylinder(h=10, d = dlense, $fn = faces_of_tube_lense_holder);  
  cylinder(h=10, d = 45, $fn = faces_of_tube_lense_holder); 
};   
};

//translate([0,-52,0])
//cube([100,1,100]);

//translate([0,-152,0])
//cube([100,1,100]);


