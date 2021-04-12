package com.shahnawaz.pws.controllers;

import com.shahnawaz.pws.entities.Order;
import com.shahnawaz.pws.entities.PwsUser;
import com.shahnawaz.pws.entities.Role;
import com.shahnawaz.pws.export.Orders;
import com.shahnawaz.pws.export.OrdersAdminExport;
import com.shahnawaz.pws.jwtFilter.JwtUtil;
import com.shahnawaz.pws.repos.OrderRepo;
import com.shahnawaz.pws.repos.RoleRepo;
import com.shahnawaz.pws.repos.UserRepo;
import com.shahnawaz.pws.reqBodies.CreateOrderDTO;
import com.shahnawaz.pws.reqBodies.GenerateOtpReq;
import com.shahnawaz.pws.resBodies.ExportAdmin;
import com.shahnawaz.pws.resBodies.JwtResponse;
import com.shahnawaz.pws.resBodies.SuccessRes;
import com.shahnawaz.pws.security.MyUserDetailService;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


@org.springframework.web.bind.annotation.RestController
//@CrossOrigin("http://localhost:4200")
@CrossOrigin(origins = "https://nawaz1650.github.io")
public class RestController {
    @Autowired
    RoleRepo roleRepo;
    public static final String ACCOUNT_SID = System.getenv("account_sid");
    public static final String AUTH_TOKEN = System.getenv("auth_token");
    @Autowired
    AuthenticationManager authmanager;
    @Autowired
    MyUserDetailService myUserDetailService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    OrderRepo orderRepo;
    @Autowired
    UserRepo userRepo;
    @GetMapping("/test")
    public String test(){
        System.out.println("hi from test end point");
        return "hi world";
    }
    @PostMapping("/generateOtp")
    public SuccessRes generateOtp(@RequestBody GenerateOtpReq req) throws Exception {
        String otp;
        Random rnd=new Random();
        otp=""+rnd.nextInt(999999);
        System.out.println(otp);
        PwsUser user1=userRepo.findByMobile(req.getMobile());
        String hashPassword= passwordEncoder.encode(otp);

        System.out.println(user1);
        if(req.getMobile()==null){
            System.out.println("from line 75");
            throw new Exception("mobile number cannot be empty");
        }
        if(user1==null) {
            System.out.println("from line 79");
            PwsUser user = new PwsUser();
            user.setMobile(req.getMobile());
            user.setOtp(hashPassword);
            try {
                userRepo.save(user);
            }catch(Exception e){
                System.out.println("from line 86");
                throw new Exception("error while saving order");
            }

        }
        else{
            System.out.println("from line 92");
            user1.setOtp(hashPassword);
            try {
                userRepo.save(user1);
            }catch (Exception e){
                System.out.println("from line 97");
                throw new Exception("exception while generating OTP");
            }

        }



            String prefix="+91";
        System.out.println("account_sid is "+System.getenv("account_sid"));
        System.out.println("auth token is "+System.getenv("auth_token"));
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        try {
            Message message = Message.creator(
                    new PhoneNumber(prefix + "" + req.getMobile()),new PhoneNumber("+15206399497"),
                    "This is your One time password to login to Patel Water supply system " + otp).create();
        }catch(Exception e){
            System.out.println("from exception");
            System.out.println(prefix + "" + req.getMobile());
            System.out.println(e.getMessage());
            System.out.println();
            System.out.println(e.getStackTrace());
            throw new Exception("exception in sending sms");

        }

            return new SuccessRes("true");
    }


    @PostMapping("/createOrder")
    public SuccessRes createOrder(@RequestBody CreateOrderDTO order,HttpServletRequest req) throws Exception {
        System.out.println(order);
        Date date=new Date(Long.parseLong(order.getTimestamp()));
        System.out.println(date);
        //TODO
       // create new order
            Order order1=new Order();
            order1.setQty(order.getQty());
            order1.setOrder_date(new Date(Long.parseLong(order.getTimestamp())));
            order1.setAddress(order.getAddress());
            PwsUser user=userRepo.findByMobile(jwtUtil.extractUsername(req.getHeader("Authorization").substring(7)));
            if(user!=null){
                order1.setUser(user);
            }
            else{
                throw new Exception("user not found");
            }
            try{
                orderRepo.save(order1);
            }catch(Exception e ){
                throw new Exception("some error occured while saving your order");
            }
          //  set user object to it then save order and return success
        System.out.println("return success  result from otp generation");
        return new SuccessRes("true");
    }


    @GetMapping("/orders")
    public List<Order> getAllOrders(HttpServletRequest request){
        String username=jwtUtil.extractUsername(request.getHeader("Authorization").substring(7));
        System.out.println("uesrname extracted from token is "+username);
        PwsUser user=userRepo.findByMobile(username);
        if(user!=null){
            List<Order> orders=user.getOrders();
            return orders;
        }
        return null;
    }

@PostMapping("/verifyOtp")
    public Object verifyOtp(@RequestBody GenerateOtpReq req) throws Exception {
        PwsUser user1=userRepo.findByMobile(req.getMobile());
        if(user1!=null) {
            System.out.println("from line 64");
            System.out.println("req otp "+passwordEncoder.encode(req.getOtp()));
            System.out.println("DB otp "+user1.getOtp());
//            if (passwordEncoder.encode(req.getOtp()).compareTo(user1.getOtp()) == 0) {
                System.out.println("from line 66");
                try{
                    System.out.println("from line 68");
                    authmanager.authenticate(new UsernamePasswordAuthenticationToken(user1.getMobile(),req.getOtp()));
                }catch (Exception e){
                    System.out.println("from line 71");
                    throw new Exception("something went wrong");
                }
                System.out.println("from line 74");
                JwtResponse jResponse= new JwtResponse( jwtUtil.generateToken(myUserDetailService.loadUserByUsername(user1.getMobile())));
                System.out.println(jResponse);
                return jResponse;
//            } else{
//                System.out.println("from line 79");
//                return new SuccessRes("false");
//            }

        }
        else
            return new SuccessRes("false");
}
@GetMapping("/admin")
    public void adminPanel(){
        System.out.println("hi admin");
}

@GetMapping("/export")
    public void export(HttpServletRequest req, HttpServletResponse res) throws IOException {

    res.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    String hedaerkey="Content-Disposition";
    String hedaerValue="attachment;filename=Todos.xlsx";
    res.setHeader(hedaerkey, hedaerValue);



    //List<Todo> todos=todorepo.
    String username=jwtUtil.extractUsername(req.getHeader("Authorization").substring(7));
    PwsUser user=userRepo.findByMobile(username);
    List<Order> orders;

        orders=user.getOrders();

        Orders o=new Orders(orders);
        o.export(res);




    return ;

}



    @GetMapping("/exportAdmin")
    public void exportAdmin(HttpServletRequest req, HttpServletResponse response) throws IOException {

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String hedaerkey="Content-Disposition";
        String hedaerValue="attachment;filename=Orders.xlsx";
        response.setHeader(hedaerkey, hedaerValue);



        List<ExportAdmin> orders=new ArrayList<>();
        System.out.println("from ordersforadmin endpoint");
        List<Object[]> result=orderRepo.findAllOrders();
        for (Object[] res:result
        ) {
 orders.add(new ExportAdmin((int)res[0],(String)res[1],(int)res[2],(int)res[3],(String)res[4],(Date)res[5],(Date) res[6]));
             }


        OrdersAdminExport exportobj=new OrdersAdminExport(orders);
        exportobj.export(response);






        return ;

    }
@PostMapping("/adminlogin")
    public Object adminLogin(@RequestBody GenerateOtpReq req) throws Exception {
        if(req.getMobile()!=null&& req.getMobile()!="") {
            PwsUser user2=userRepo.findByMobile(req.getMobile());
            List<Role> roles=user2.getRoles();
            if(roles!=null) {
                if(roles.isEmpty()){
                    throw new Exception("user is not an Admin user!!");
                }
                if(roles.get(0).getName().compareToIgnoreCase("ADMIN")==0){
                    String otp;
                    Random rnd=new Random();
                    otp=""+rnd.nextInt(999999);
                    System.out.println(otp);
                   // PwsUser user1=userRepo.findByMobile(req.getMobile());
                    String hashPassword= passwordEncoder.encode(otp);

                    System.out.println(user2);
                    if(req.getMobile()==null)
                        throw new Exception("mobile number cannot be empty");
                    if(user2==null) {
                        PwsUser user = new PwsUser();
                        user.setMobile(req.getMobile());
                        user.setOtp(hashPassword);
                        try {
                            userRepo.save(user);
                        }catch(Exception e){
                            throw new Exception("error while saving order");
                        }

                    }
                    else{
                        user2.setOtp(hashPassword);
                        try {
                            userRepo.save(user2);
                        }catch (Exception e){
                            throw new Exception("exception while generating OTP");
                        }

                    }
                    return new SuccessRes("true");
                }
                System.out.println(roles);
            }else{
            throw new Exception("user doesnt exist or not an Admin account");
            }
            return null;
        }
        else{
            return null;
        }

}


    @GetMapping("/ordersforadmin")
    public List<ExportAdmin> getAllOrdersAdmin(HttpServletRequest request){
        //String username=jwtUtil.extractUsername(request.getHeader("Authorization").substring(7));
        //System.out.println("uesrname extracted from token is "+username);
        //PwsUser user=userRepo.findByMobile(username);
//        if(user!=null){
//            List<Order> orders=user.getOrders();
//            return orders;
//        }
        List<ExportAdmin> orders=new ArrayList<>();
        System.out.println("from ordersforadmin endpoint");
        List<Object[]> result=orderRepo.findAllOrders();
        for (Object[] res:result
             ) {
                //new Order(res[0]);
            //select u.user_id,u.mobile,O.order_id,O.qty,O.address,O.order_date,O.created_date from Order O JOIN O.user u")


            orders.add(new ExportAdmin((int)res[0],(String)res[1],(int)res[2],(int)res[3],(String)res[4],(Date)res[5],(Date) res[6]));
            System.out.println(res[0]);
            System.out.println(res[0].getClass());
            System.out.println();
            System.out.println(res[1]);
            System.out.println(res[1].getClass());
            System.out.println();
            System.out.println(res[2]);
            System.out.println(res[2].getClass());
            System.out.println();
            System.out.println(res[3]);
            System.out.println(res[3].getClass());
            System.out.println();
            System.out.println(res[4]);
            System.out.println(res[4].getClass());
            System.out.println();
            System.out.println(res[5]);
            System.out.println(res[5].getClass());
            System.out.println();
            System.out.println(res[6]);
            System.out.println(res[6].getClass());

        }
        System.out.println(orders);
        return orders;
        //return null;
    }

    @GetMapping("/onetime")
    public void onetime(){
    PwsUser user=new PwsUser();
    user.setMobile("7977956098");
    Role role=new Role();

//    role.setRole_id(2);
    role.setName("ADMIN");
    List<Role> roles=new ArrayList<>();
    roles.add(role);

    user.setRoles(roles);
//    user.setUser_id(1);
        System.out.println(userRepo.save(user));

    }


}
