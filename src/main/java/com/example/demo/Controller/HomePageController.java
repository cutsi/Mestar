package com.example.demo.Controller;

import com.example.demo.Models.*;
import com.example.demo.Services.*;
import com.example.demo.Requests.AdRequest;
import com.example.demo.Requests.UserRequest;
import com.example.demo.Utils.appuser.AppUserRole;
import com.example.demo.Utils.email.EmailSender;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping(path = "/")
@AllArgsConstructor
public class HomePageController {
    //TODO 1. ADMIN PAGE 2. REVIEWS 3. RATINGS 4. FRONTEND 5. user ads 6. Messages
    private final AppUserService appUserService;
    private final CategoryService categoryService;
    private final CityService cityService;
    private final MessageService messageService;
    private final AdService adService;
    private final IconService iconService;
    private final EmailSender emailSender;
    private final ApplyService applyService;

    @GetMapping(path = "/welcome")
    public String index(Model model) {
        Set<AppUser> handyMen = appUserService.getAllUsersByContractorRole();
        Optional<AppUser> currentUser = appUserService.getCurrentUser();//TODO getCurrentUser vraca optional
        List<Ad> allAds = adService.getAllAds();
        List lastNineAds = new ArrayList();
        if(currentUser.isPresent()){
            for(int i = allAds.size()-1; i>0;i--) {
                System.out.println("ADS: " + allAds.get(i).getId());
                if(allAds.get(i).getUser().getId() != currentUser.get().getId() && !allAds.get(i).isFinished())
                    lastNineAds.add(allAds.get(i));
                if (lastNineAds.size()==9)
                    break;
            }
            model.addAttribute("handymen", handyMen);
            model.addAttribute("ads", lastNineAds);
            model.addAttribute("user", currentUser.get());
        }
        else{
            for(int i = allAds.size()-1; i>0;i--) {
                if(!allAds.get(i).isPicked() && !allAds.get(i).isFinished())
                    lastNineAds.add(allAds.get(i));
                if (lastNineAds.size()==9)
                    break;
            }
            model.addAttribute("handymen", handyMen);
            model.addAttribute("ads", lastNineAds);
            model.addAttribute("user", new AppUser("user"));
        }
        return "welcome";
    }
    @PostMapping(path="chatPost")
    public String postChat(Model model,
                           @RequestParam("sender") String senderId,
                           @RequestParam("receiver") String receiverId,
                           @RequestParam("message") String message){
        Message msg = new Message(message,true,true,true,
                appUserService.getUserById(Long.valueOf(senderId)).get(),
                Long.valueOf(receiverId));
        messageService.SaveMessage(msg);


        Set<Long> idsFromAllUserConversations =
                messageService.mergeSet(messageService.getAllReceiversFromCurrentUser
                                (appUserService.getCurrentUser().get()),
                        messageService.getAllSendersFromCurrentUser
                                (appUserService.getCurrentUser().get()));
        ArrayList<AppUser> conversationUsers = new ArrayList<>();
        for (Long ids:idsFromAllUserConversations) {
            conversationUsers.add(appUserService.getUserById(ids).get());
        }
        model.addAttribute("messages", messageService.getConversationBetweenReceiverAndSender(appUserService.getCurrentUser().get(), appUserService.getUserById(Long.valueOf(receiverId)).get()));
        model.addAttribute("convos",conversationUsers);
        model.addAttribute("receiver", appUserService.getUserById(Long.valueOf(receiverId)).get());
        model.addAttribute("currentUser", appUserService.getCurrentUser().get());
        return "chat";
    }
    @GetMapping(path="persons/all")
    public String datatable(){
        ///"[{id:1,firstname='Steve', lastname:'stevic', },{}]"

        return "test.html";
    }
    @PostMapping(path = "/pickedContractor")
    public String pickedContractor(@RequestParam("adId") String adId,
                                   @RequestParam("pickedCon") String conId,
                                   Model model){
        Optional<Ad> optAd = adService.getAdById(Long.valueOf(adId));
        Optional<AppUser> optCon = appUserService.getUserById(Long.valueOf(conId));
        Ad ad = new Ad();
        AppUser contractor = new AppUser();
        if(optCon.isPresent() && optAd.isPresent()){
            ad = optAd.get();
            contractor = optCon.get();
        }
        ad.setPicked(true);
        adService.saveAd(ad);
        model.addAttribute("contractor", contractor);
        model.addAttribute("ad", ad);
        return "rateUser";
    }

    @GetMapping(path = "chat")
    public String getChat(Model model, @RequestParam("id") String id){
        System.out.println("ID KOJI ME JEVE JE: " + id);
        Set<Long> idsFromAllUserConversations =
                messageService.mergeSet(messageService.getAllReceiversFromCurrentUser(
                        appUserService.getCurrentUser().get()),
                        messageService.getAllSendersFromCurrentUser(appUserService.getCurrentUser().get()));
        ArrayList<AppUser> conversationUsers = new ArrayList<>();
        for (Long ids:idsFromAllUserConversations) {
            conversationUsers.add(appUserService.getUserById(ids).get());
        }
        for (AppUser user:conversationUsers) {
            System.out.println("IMENA USERA: " + user.getFirstName());
        }
        for (Message m:messageService.getConversationBetweenReceiverAndSender(appUserService.getCurrentUser().get(), appUserService.getUserById(Long.valueOf(id)).get())) {
            System.out.println("RAZGOVORI OD USERA: " + m.getContent());
        }
        conversationUsers.sort(Comparator.comparing(AppUser::getId));

        model.addAttribute("messages", messageService.getConversationBetweenReceiverAndSender(appUserService.getCurrentUser().get(), appUserService.getUserById(Long.valueOf(id)).get()));
        model.addAttribute("convos",conversationUsers);
        model.addAttribute("receiver", appUserService.getUserById(Long.valueOf(id)).get());
        model.addAttribute("currentUser", appUserService.getCurrentUser().get());
        //TODO 1. implement user rating and review
        //TODO 2. popravit stranicu u inboxu i chatu, stavit textarea !!!RJEŠENO!!!
        //TODO 3. Omogucit tables !!!RJEŠENO!!!
        //TODO 4. Omogucit upload slike profila
        //TODO 5. RJESIT APPLY FOR AD
        //TODO 6. SREDIT HTML ZA PICK HANDYMAN !!!RJEŠENO!!!
        //TODO 7. SREDIT HTML ZA APPLY TO BECOME HANDYMAN
        //TODO 8. NAPRAVIT VIŠE RAČUNA KORISNIKA I HANDYMENA I REKLAMA
        //TODO 9. SLAT U WELCOME PAGE 9 NAJBOLJE OCJENJENIH HANDYMENA
        //TODO 10. SLAT U WELCOME PAGE 9 NAJNOVNIJIH REKLAMA !!!RJEŠENO!!!
        //TODO 11. POREDAT PORUKE PO DATUMU SLANJA !!!RJEŠENO!!!
        return "chat";
    }
    @GetMapping(path = "/showAd")
    public String showAd(Model model, @RequestParam("id") String id){
        Ad ad = adService.getAdById(Long.valueOf(id)).get();
        System.out.println("AD ID: " + ad.getTitle());
        Optional<AppUser> appUserOptional = appUserService.getUserById(ad.getUser().getId());
        Optional<AppUser> currentUserOpt = appUserService.getCurrentUser();
        ad.setPicked(true);
        adService.saveAd(ad);
        AppUser appUser;
        AppUser currentUser;
        String typeOfUser = "user";
        if(appUserOptional.isPresent())
            appUser = appUserOptional.get();
        else
            return "fail";
        if(currentUserOpt.isPresent()){
            currentUser = currentUserOpt.get();
            if(currentUser.getAppUserRole().equals(AppUserRole.CONTRACTOR))
                typeOfUser = "contractor";
        }
        else
            currentUser = new AppUser("user");
        if(!applyService.getAllByAdIdAndContractorId(ad.getId(), currentUser.getId()).isEmpty())
            typeOfUser = "applied";
        if(adService.getAdById(ad.getId()).get().isFinished())
            typeOfUser = "finished";
        model.addAttribute("ad", ad);
        model.addAttribute("user", appUser);
        model.addAttribute("categories", appUserService.getAllCategoriesFromUser(appUser.getEmail()));
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("CONTRACTOR", AppUserRole.CONTRACTOR);
        model.addAttribute("type", typeOfUser);
        return "showAd";
    }

    @GetMapping("/showFinishedAd")
    public String showFinishedAd(Model model, @RequestParam("id") String id){
        Ad ad = adService.getAdById(Long.valueOf(id)).get();
        System.out.println("AD ID: " + ad.getTitle());
        Optional<AppUser> appUserOptional = appUserService.getUserById(ad.getUser().getId());
        Optional<AppUser> currentUserOpt = appUserService.getCurrentUser();
        AppUser appUser;
        AppUser currentUser;
        String typeOfUser = "user";
        if(appUserOptional.isPresent())
            appUser = appUserOptional.get();
        else
            return "fail";
        if(currentUserOpt.isPresent()){
            currentUser = currentUserOpt.get();
            if(currentUser.getAppUserRole().equals(AppUserRole.CONTRACTOR))
                typeOfUser = "contractor";
        }
        else
            currentUser = new AppUser("user");
        if(!applyService.getAllByAdIdAndContractorId(ad.getId(), currentUser.getId()).isEmpty())
            typeOfUser = "applied";
        String message = "THIS AD IS CLOSED.";
        if(applyService.getByAdIdAndContractorId(ad.getId(),currentUser.getId()).get().getContractorId().equals(currentUser.getId()))
            message = "You already applied for this ad.";
        model.addAttribute("ad", ad);
        model.addAttribute("user", appUser);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("message", message);
        return "showFinishedAd";
    }
    @GetMapping(path = "/showMyAd")
    public String showMyAd(Model model, @RequestParam("id") String id){
        Ad ad = adService.getAdById(Long.valueOf(id)).get();

        System.out.println("AD ID: " + ad.getTitle());
        Optional<AppUser> appUserOptional = appUserService.getUserById(ad.getUser().getId());
        Optional<AppUser> currentUserOpt = appUserService.getCurrentUser();
        List <Apply> applied = applyService.getAllByAdId(ad.getId());
        List<AppUser> contractorsThatAppliedForAd = new ArrayList<>();
        for (Apply apply:applied) {
            contractorsThatAppliedForAd.add(appUserService.getUserById(apply.getContractorId()).get());
        }

        AppUser appUser;
        AppUser currentUser;
        if(appUserOptional.isPresent())
            appUser = appUserOptional.get();
        else
            return "fail";
        if(currentUserOpt.isPresent()){
            currentUser = currentUserOpt.get();
        }
        else
            currentUser = new AppUser("user");

        model.addAttribute("ad", ad);
        model.addAttribute("user", appUser);
        model.addAttribute("categories", appUserService.getAllCategoriesFromUser(appUser.getEmail()));
        Set<AppUser> handyMen = appUserService.getAllUsersByContractorRole();
        model.addAttribute("handymen", contractorsThatAppliedForAd);

        return "showMyAd";
    }
    @GetMapping(path = "/my-profile")
    public String myProfile(Model model){
        model.addAttribute("user", appUserService.getCurrentUser().get());
        System.out.println("HTML CONTROLLER: " + appUserService.getCurrentUser().get().getPhone());
        model.addAttribute("categories", appUserService.getAllCategoriesFromUser(appUserService.getCurrentUser().get().getEmail()));
        List<Ad> adSet = adService.getAdByUser(appUserService.getCurrentUser().get());
        List<Ad> finishedAds = adService.getAllByFinishedTrueAndUser(appUserService.getCurrentUser().get());
        List<Ad> onGoingAds = adService.getAllByPickedFalseAndUser(appUserService.getCurrentUser().get());
        List<Ad> inTheProcessAds = adService.getAllByPickedTrueAndUser(appUserService.getCurrentUser().get());
        String currentUserRole = appUserService.getCurrentUser().get().getAppUserRole().toString();
        if(appUserService.getCurrentUser().get().getAppUserRole().equals(AppUserRole.CONTRACTOR)){
            List<Apply> applyList = applyService.getAllByContractorId(appUserService.getCurrentUser().get().getId());
            List<Ad> adList = new ArrayList<>();
            for (Apply apply:applyList) {

                if(adService.getAdById(apply.getAdId()).get().isPicked() &&
                        !adService.getAdById(apply.getAdId()).get().isFinished())
                adList.add(adService.getAdById(apply.getAdId()).get());
                System.out.println("JOBS USER APPLIED FOR: " + apply.getAdId());
            }
            for (Ad ad:adList
                 ) {
                System.out.println("JOBS USER APPLIED FOR: " + ad.getId());

            }
            model.addAttribute("appliedJobs",adList);
        }
        List<Ad> usersAds = adService.getAllByUser(appUserService.getCurrentUser().get());
        List<Apply> applyList = applyService.getAllByContractorId(appUserService.getCurrentUser().get().getId());
        List<Ad> adAppliedList = new ArrayList<>();
        for (Apply apply:applyList) {
            adAppliedList.add(adService.getAdById(apply.getAdId()).get());
        }

        model.addAttribute("inTheProcessAds", inTheProcessAds);
        model.addAttribute("ongoingAds", onGoingAds);
        model.addAttribute("currentUserRole", currentUserRole);
        model.addAttribute("finishedAds", finishedAds);
        model.addAttribute("ads", adSet); //TODO popravit da ne mogu izabrat contractora kad klikem na reklame koje su vec odabrane ili zavrsene
        model.addAttribute("usersAds", usersAds);
        model.addAttribute("adAppliedList", adAppliedList);
        model.addAttribute("currentUser", appUserService.getCurrentUser().get());
        return "my-profile";
    }
    //check-contractor
    @PostMapping("/msgSearch")
    public String checkContractor(@RequestParam("id") String id, Model model){
        Optional<AppUser> appUserOptional = appUserService.getUserById(Long.valueOf(id));
        AppUser appUser = new AppUser();
        if(appUserOptional.isPresent())
            appUser = appUserOptional.get();
        else
            return "fail";

        model.addAttribute("user", appUser);
        model.addAttribute("categories", appUserService.getAllCategoriesFromUser(appUser.getEmail()));
        return "user-profile";
    }
    @GetMapping("/rate")
    public String checkContractor(@RequestParam("rate") String rating,
                                  @RequestParam("contractorId") String contractorId,
                                  @RequestParam("adId") String adId,
                                  Model model){


        AppUser contractor = appUserService.getUserById(Long.valueOf(contractorId)).get();
        contractor.setNumberOfRatings(contractor.getNumberOfRatings()+1);
        double ratingAvg = contractor.getRating();
        ratingAvg = ratingAvg + Double.valueOf(rating);
        ratingAvg = ratingAvg/Double.valueOf(contractor.getNumberOfRatings());
        contractor.setRating(ratingAvg);
        appUserService.saveUser(contractor);
        List<Ad> allAds = adService.getAllAds();
        List lastNineAds = new ArrayList();

        for(int i = allAds.size()-1; i>0;i--) {
            System.out.println("ADS: " + allAds.get(i).getId());
            if(allAds.get(i).getUser().getId() != appUserService.getCurrentUser().get().getId() && !allAds.get(i).isFinished())
                lastNineAds.add(allAds.get(i));
            if (lastNineAds.size()==9)
                break;
        }
        Set<AppUser> handyMen = appUserService.getAllUsersByContractorRole();

        model.addAttribute("handymen", handyMen);
        model.addAttribute("ads", lastNineAds);
        model.addAttribute("user", appUserService.getCurrentUser().get());
        return "welcome";
    }
    @GetMapping("/usersAd/{id}/")
    public String usersAd(@RequestParam(value="id",required=false) String id, Model model){
        Optional<AppUser> appUserOptional = appUserService.getUserById(Long.valueOf(id));
        AppUser appUser = new AppUser();
        if(appUserOptional.isPresent())
            appUser = appUserOptional.get();
        else
            return "about-us"; //TODO handle exception here

        model.addAttribute("user", appUser);
        model.addAttribute("categories", appUserService.getAllCategoriesFromUser(appUser.getEmail()));
        return "user-profile";
    }
    /*@PostMapping(path = "/send-message-to-user")
    public String SendMsgToUser(){

    }*/
    @GetMapping("/msgSearch")
    public String checkContractorWelcome(@RequestParam("id") String id, Model model){
        Optional<AppUser> appUserOptional = appUserService.getUserById(Long.valueOf(id));
        AppUser appUser;
        Optional<AppUser> currentUserOpt = appUserService.getCurrentUser();
        AppUser currentUser;
        if(currentUserOpt.isPresent())
            currentUser = currentUserOpt.get();
        else
            currentUser = new AppUser("user");
        if(appUserOptional.isPresent())
            appUser = appUserOptional.get();
        else
            return "about-us"; //TODO handle exception here

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("CONTRACTOR", AppUserRole.CONTRACTOR);
        model.addAttribute("USER", AppUserRole.USER);

        model.addAttribute("user", appUser);
        model.addAttribute("categories", appUserService.getAllCategoriesFromUser(appUser.getEmail()));
        return "user-profile";
    }
    @GetMapping(path = "/browseAds")
    public String browseAds(Model model){
        List<Ad> adSet = adService.getAllAds();
        model.addAttribute("ads", adSet);
        return "browseAds";
    }
    @GetMapping(path = "/home")
    public String home(){
        return "home";
    }
    @GetMapping(path = "/EditProfile")
    public String editProfile(Model model){
        model.addAttribute("user", appUserService.getCurrentUser().get());
        model.addAttribute("categories", appUserService.getAllCategoriesFromUser(appUserService.getCurrentUser().get().getEmail()));
        return "EditProfile";
    }
    @RequestMapping(value = "edit-me", method = RequestMethod.POST)
    public String editProfilePost(@ModelAttribute UserRequest userRequest,
                                  Model model){
        AppUser user = appUserService.getCurrentUser().get();
        user.setFirstName(userRequest.getName());
        user.setLastName(userRequest.getLastname());
        user.setPhone(userRequest.getPhone());
        user.setAddress(userRequest.getAddress());
        user.setEmail(userRequest.getEmail());
        appUserService.saveUser(user);
        System.out.println("USERS NAME: " + userRequest.getName());
        Set<AppUser> handyMen = appUserService.getAllUsersByContractorRole();
        model.addAttribute("handymen", handyMen);
        model.addAttribute("user", user);
        model.addAttribute("ads", adService.getAllAds());
        return "welcome";
    }
    @GetMapping(path = "/about-us")
    public String aboutUs(){
        return "about-us";
    }
    @GetMapping(path = "/pick")
    public String pick(Model model){
        List<String> categories = categoryService.getAllCategories();
        List<String> cities = cityService.getAllCities();
        model.addAttribute("userList", categories);
        model.addAttribute("cityList", cities);
        Set<AppUser> handymen = appUserService.getAllUsersByContractorRole();
        model.addAttribute("handymen", handymen);
        return "pick";
    }

    @RequestMapping(value = "about-us", method = RequestMethod.POST) //TODO smanjit ovu funk priko servisa
    public String pickPostt(@RequestParam("city") String city,
                           @RequestParam("category") String category){
        System.out.println("pickPost PROFFESIONS AREEEE:  " + city + " " + category);
        List<String> cities = Arrays.asList(city.split(","));
        List<String> categories = Arrays.asList(category.split(","));
        System.out.println("LISTS AREEEE:  " + cities + " " + categories);
        List<City> cityObjList = new ArrayList<>();
        List<Category> categoryObjList = new ArrayList<>();
        for (String c : cities) {
            cityObjList.add(cityService.getCityByName(c).get());
        }
        for (String c: categories) {
            categoryObjList.add(categoryService.getCategoryByCategoryName(c).get());
        }
        AppUser appUser = appUserService.getCurrentUser().get();
        for (City c:cityObjList) {
            appUser.addCityToUser(c);
        }
        for (Category cat:categoryObjList) {
            appUser.addCategoryToUser(cat);
        }
        appUserService.saveUser(appUser);
            return "/about-us";
    }
    @GetMapping(path = "/checkoutHandyman")
    public String checkoutHandyman(@RequestParam("id") String id){
        System.out.println("INTEGER ID:" + id);
        return "about-us";
    }
    @RequestMapping(value = "about-you", method = RequestMethod.POST)//TODO smanjit i ovu funk priko servisa
    public String pickPost(@RequestParam("city") String city,
                            @RequestParam("proffesions") String category,
                            Model model){
        System.out.println("pickPostt PROFFESIONS ARE:  " + city + " " + category);
        Optional<City> cityOpt = cityService.getCityByName(city);
        Optional<Category> catOpt = categoryService.getCategoryByCategoryName(category);
        if(cityOpt.isPresent()){
            City cityObj = cityOpt.get();
        }
        if(catOpt.isPresent()){
            Category catObj = catOpt.get();
        }
        Set<AppUser> usersByCategory = appUserService.getAllUsersByCategory(category);
        Set<AppUser> usersByCities = appUserService.getAllUsersByCity(city);
        for (AppUser userCat:usersByCategory) {
            System.out.println("ALL USERS IN CATEGORY: " + category + " " + userCat.getUsername());
        }
        for (AppUser userCity:usersByCities) {
            System.out.println("ALL USERS IN CITY: " + city + " " + userCity.getUsername());
        }
        Set<AppUser> usersByCatAndCity = appUserService.getAllUsersByCategoryAndCity(usersByCategory, usersByCities);
        for (AppUser userByCatAndCity:usersByCatAndCity) {
            System.out.println("INTERSECTION OF ALL USERS: " + city + " " + category + " " + userByCatAndCity.getUsername());
        }
        model.addAttribute("users", usersByCatAndCity);
        return "/picked_contractors";
        //TODO 1. prikazat korisnike na pick stranici (moguce na novoj)
    }
    @GetMapping(path = "/success")
    public String success(){
        return "success";
    }
    @PostMapping(path = "/success")
    public String successPost(){
        System.out.println("SUCCESS POST");
        return "about-us";
    }
    @GetMapping(path = "/fail")
    public String fail(){
        return "fail";
    }
    @GetMapping(path = "/construct-ad")
    public String constructAd(Model model){
        List<String> categories = categoryService.getAllCategories();
        List<String> cities = cityService.getAllCities();
        model.addAttribute("userList", categories);
        model.addAttribute("cityList", cities);
        return "construct-ad";
    }
    @PostMapping(path = "/construct-ad")
    public String constructAdPost(Model model,
                                  @ModelAttribute AdRequest adRequest){
        Ad ad = new Ad
                (categoryService.getCategoryByCategoryName(adRequest.getCategory()).get(),
                cityService.getCityByName(adRequest.getCity()).get(),
                LocalDateTime.now(),
                adRequest.getDescription(),
                adRequest.getPrice(),
                adRequest.getTitle(),
                appUserService.getCurrentUser().get(),
                iconService.getIconByCategory(categoryService.getCategoryByCategoryName
                        (adRequest.getCategory()).get()));
        adService.saveAd(ad);

        model.addAttribute("user", appUserService.getCurrentUser().get());
        if(appUserService.getCurrentUser().get() != null){
            model.addAttribute("user", appUserService.getCurrentUser().get());
            model.addAttribute("ads", adService.getAllAds());
            return "welcome";
        }
        else{
            model.addAttribute("user", new AppUser("user"));
            model.addAttribute("ads", adService.getAllAds());
            return "welcome";
        }
    }
    @GetMapping(path = "/handyman")
    public String handyman(Model model){
        List<String> cities = cityService.getAllCities();
        List<String> categories = categoryService.getAllCategories();

        model.addAttribute("cityList", cities);
        model.addAttribute("categoryList", categories);
        return "becomeHandy";
    }

    @GetMapping("modal1")
    public String modal1() {
        return "modal1";
    }

    @GetMapping("modal2")
    public String modal2(@RequestParam("name") String name, Model model) {
        model.addAttribute("name", name);
        return "modal2";
    }
    @PostMapping(path = "send-message")
    public String send_message(@RequestParam("contractor") String contractorId,
                               @RequestParam("ad") String adId){

        System.out.println("MESSAGE: " + contractorId + " " +adId);
        Optional<AppUser> optContractor = appUserService.getUserById(Long.valueOf(contractorId));
        Optional<Ad> optAd = adService.getAdById(Long.valueOf(adId));
        AppUser contractor, adOwner;
        Ad ad;

        if(optContractor.isPresent() && optAd.isPresent()){
            contractor = optContractor.get();
            ad = optAd.get();
            adOwner = ad.getUser();
        }
        else
            return "fail";
        Message msg = new Message("I would like to apply for this ad:"
                + "(Ad id: " + adService.getAdById(Long.valueOf(adId)).get().getId() + ")"
                ,true,true,true,null,
                adService.getAdById(Long.valueOf(adId)).get().getUser().getId());

        String link = "http://localhost:8080/inbox";
        Apply apply = new Apply(contractor.getId(), adOwner.getId(), ad.getId());
        applyService.saveApply(apply);
        String emailContent = buildEmail(adService.getAdById(Long.valueOf(adId)).get().getUser(),appUserService.getUserById(Long.valueOf(contractorId)).get(),link, msg);

        emailSender.send(adService.getAdById(Long.valueOf(adId)).get().getUser().getEmail(), emailContent);

        return "success";
    }
    @GetMapping(path = "/inbox")
    public String inbox(Model model){
        Set<Long> idsFromAllUserConversations =
                messageService.mergeSet(messageService.getAllReceiversFromCurrentUser(
                        appUserService.getCurrentUser().get()),
                messageService.getAllSendersFromCurrentUser(appUserService.getCurrentUser().get()));
        ArrayList<AppUser> conversationUsers = new ArrayList<>();
        for (Long id:idsFromAllUserConversations) {
            conversationUsers.add(appUserService.getUserById(id).get());
        }

        for (AppUser user:conversationUsers) {
            System.out.println("IMENA USERA: " + user.getFirstName());
        }
        conversationUsers.sort(Comparator.comparing(AppUser::getId));

        model.addAttribute("convos",conversationUsers);
        return "inbox";
    }

    private String buildEmail(AppUser receiver, AppUser sender, String link, Message message) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">New Message</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + receiver.getFirstName() + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> You have a new message from " + sender.getFirstName() + " " + sender.getLastName() + ":" + "<br><br>-" + message.getContent() + "<br><br> Click on the link below to respond. </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Respond</a> </p></blockquote>\n </p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }
}
