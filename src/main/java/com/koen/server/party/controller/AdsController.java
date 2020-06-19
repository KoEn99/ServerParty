package com.koen.server.party.controller;

import com.koen.server.party.dto.*;
import com.koen.server.party.entity.*;
import com.koen.server.party.repository.*;
import com.koen.server.party.security.jwt.JwtUser;
import com.koen.server.party.service.*;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.ServletContext;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.parser.Entity;
import java.awt.*;
import java.io.*;
import java.lang.reflect.Field;
import java.nio.DoubleBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.*;
import java.util.List;

@RestController
@RequestMapping("/ads")
@PropertySource(value={"classpath:application.properties"})
public class AdsController{
    @Autowired
    AuthPersonRepository authPersonRepository;
    @Autowired
    AdsReviewRepository adsReviewRepository;
    @Autowired
    AdsRepository adsRepository;
    @Autowired
    private AdsReviewService adsReviewService;
    @Autowired
    private AdsImageRepository adsImageRepository;
    @Autowired
    private AdsImageService adsImageService;
    @Autowired
    private AdsService adsService;
    @Autowired
    private AuthService authService;
    @Autowired
    private AdsFavoritesService adsFavoritesService;
    @Autowired
    private AdsFavoritesRepository adsFavoritesRepository;
    @Value("${upload.path}")
    String uploadpath;
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity addAccount(@RequestBody AdsPersonRequestDto adsPersonRequestDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtUser userDetails = (JwtUser) authentication.getPrincipal();
        AdsPerson adsPerson = new AdsPerson();
        adsPerson.setTitle(adsPersonRequestDto.getTitle());
        adsPerson.setCity(adsPersonRequestDto.getCity());
        adsPerson.setCategory(adsPersonRequestDto.getCategory());
        adsPerson.setPrice(adsPersonRequestDto.getPrice());
        adsPerson.setAdsProfile(adsPersonRequestDto.getAdsProfile());
        adsPerson.setAuthPerson(authPersonRepository.findByEmail(userDetails.getEmail()));
        adsPerson.getAdsProfile().setAdsPerson(adsPerson);
        adsPerson.setRating("0");
        adsService.save(adsPerson);
        for (int i = 0; i < adsPersonRequestDto.getAdsImages().size(); i++){
            AdsImage adsImage = new AdsImage();
            adsImage.setFilename(adsPersonRequestDto.getAdsImages().get(i));
            adsImage.setAdsPerson(adsPerson);
            adsImageService.save(adsImage);
        }
        return new ResponseEntity(HttpStatus.CREATED);
    }
    @RequestMapping(value = "/add-favorites", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity addFavorites(@RequestBody AdsFavoriteDto adsFavoriteDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtUser userDetails = (JwtUser) authentication.getPrincipal();
        AuthPerson authPerson = authPersonRepository.findByEmail(userDetails.getEmail());
        Optional<AdsPerson> adsPerson = adsRepository.findById(adsFavoriteDto.getId());
        AdsFavorites adsFavorites = new AdsFavorites();
        adsFavorites.setAuthPerson(authPerson);
        adsFavorites.setAdsPerson(adsPerson.get());
        adsFavoritesService.save(adsFavorites);
        return new ResponseEntity(HttpStatus.CREATED);
    }
    @RequestMapping(value = "/remove-favorites", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity removeFavorites(@RequestBody AdsFavoriteDto adsFavoriteDto){
        Optional<AdsFavorites> adsFavorites = adsFavoritesRepository.findById(adsFavoriteDto.getId());
        adsFavoritesService.remove(adsFavorites.get());
        return new ResponseEntity(HttpStatus.CREATED);
    }
    @RequestMapping(value = "/my-favorites", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getMyFavorites(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtUser userDetails = (JwtUser) authentication.getPrincipal();
        List<AdsFavorites> adsFavorites = adsFavoritesRepository.findByAuthPerson_Email(userDetails.getEmail(), Sort.by("adsPerson_id"));
        return ResponseEntity.ok(adsFavorites);
    }
    @RequestMapping(value = "/change", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity change(@RequestBody AdsPersonRequestDto adsPersonRequestDto){
        Optional<AdsPerson> adsPerson = adsRepository.findById(adsPersonRequestDto.getId());
        adsPerson.get().getAdsProfile().setDescription(adsPersonRequestDto.getAdsProfile().getDescription());
        adsPerson.get().setCategory(adsPersonRequestDto.getCategory());
        adsPerson.get().setCity(adsPersonRequestDto.getCity());
        adsPerson.get().setPrice(adsPersonRequestDto.getPrice());
        adsPerson.get().setTitle(adsPersonRequestDto.getTitle());
        AdsPerson adsPerson1 = adsPerson.get();
        adsService.save(adsPerson1);
        List<AdsImage> adsImageList = adsImageRepository.findByAdsPerson_Id(adsPersonRequestDto.getId());
        for (int i = 0; i < adsPersonRequestDto.getAdsImages().size(); i++){
            AdsImage adsImage = new AdsImage();
            if (i < adsImageList.size()) adsImage.setId(adsImageList.get(i).getId());
            adsImage.setFilename(adsPersonRequestDto.getAdsImages().get(i));
            adsImage.setAdsPerson(adsPerson.get());
            adsImageService.save(adsImage);
        }
        return new ResponseEntity(HttpStatus.CREATED);
    }
    @RequestMapping(value = "/change-profile", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity changeProfile(@RequestBody AuthPersonRequestDto authPersonRequestDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtUser userDetails = (JwtUser) authentication.getPrincipal();
        AuthPerson authPerson = authPersonRepository.findByEmail(userDetails.getEmail());
        authPerson.setEmail(authPersonRequestDto.getEmail());
        authPerson.getProfilePerson().setName(authPersonRequestDto.getName());
        authPerson.getProfilePerson().setSurname(authPersonRequestDto.getSurname());
        authPerson.getProfilePerson().setMiddleName(authPersonRequestDto.getMiddle_name());
        authPerson.getProfilePerson().setNumberPhone(authPersonRequestDto.getNumber_phone());
        authPerson.getProfilePerson().setCity(authPersonRequestDto.getCity());
        authService.save(authPerson);
        return new ResponseEntity(HttpStatus.CREATED);
    }
    @RequestMapping(value = "/image/load", method = RequestMethod.POST)
    @ResponseBody
    public Map<Object, Object> addAccount(@RequestParam("file") MultipartFile file) throws IOException {
        String filename = "";
        if (file != null){
            File uploaddir = new File(uploadpath);
            if (!uploaddir.exists()){
                uploaddir.mkdir();
            }
            String uuifile = UUID.randomUUID().toString();
            filename = uuifile + "." + file.getOriginalFilename();
            byte[] bytes = file.getBytes();
            BufferedOutputStream stream =
                    new BufferedOutputStream(new FileOutputStream(new File(uploadpath + "/" + filename)));
            stream.write(bytes);
            stream.close();
        }
        Map<Object, Object> response = new HashMap<>();
        response.put("filename", filename);
        return response;
    }
    @RequestMapping(value = "/image/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity deleteImage(@RequestBody List<String> imagelist) throws IOException {
        for (int i = 0; i < imagelist.size(); i++) {
            File file = new File(uploadpath + "/" + imagelist.get(i));
            file.delete();
            AdsImage adsImage = adsImageRepository.findByFilename(imagelist.get(i));
            if (adsImage != null) adsImageService.remove(adsImage);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
    @Autowired
    private ServletContext servletContext;
   @GetMapping(
            value = "/image/{fileName:.+}",
            produces = MediaType.ALL_VALUE
    )
    public @ResponseBody byte[] getImageAsByteArray(@PathVariable("fileName") String fileName) throws IOException {
        InputStream in = new FileInputStream(uploadpath + "/" + fileName);
        return IOUtils.toByteArray(in);
    }
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity searchAds(@RequestBody AdsSearchDto adsSearchDto){
        List<AdsPerson> adsPeople;
        if (adsSearchDto.getCategory().equals("")) {
            adsPeople = searchWithoutCategory(adsSearchDto.getTitle(), adsSearchDto.getCity(), adsSearchDto.getSort());
        } else {
            adsPeople = searchWithCategory(adsSearchDto.getTitle(), adsSearchDto.getCity(), adsSearchDto.getCategory(), adsSearchDto.getSort());
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() != "anonymousUser"){
            JwtUser userDetails = (JwtUser) authentication.getPrincipal();
            List<AdsFavorites> adsFavorites = adsFavoritesRepository.findByAuthPerson_Email(userDetails.getEmail(), Sort.by("adsPerson_id"));
            for (int i = 0; i < adsPeople.size(); i++){
                for (int j = 0; j < adsFavorites.size(); j++){
                    if (adsPeople.get(i).getId() == adsFavorites.get(j).getAdsPerson().getId()) {
                        adsPeople.get(i).setMyFavorite(adsFavorites.get(j).getId());
                    }
                }
            }
        }
        return ResponseEntity.ok(adsPeople);
    }
    List<AdsPerson> searchWithoutCategory(String title, String city, String sort){
        List<AdsPerson> adsPeople = new ArrayList<>();
        if (!city.equals(""))
            adsPeople = adsRepository.findByTitleContainingIgnoreCaseAndCity(title, city,Sort.by(sort));
        else adsPeople = adsRepository.findByTitleContainingIgnoreCase(title, Sort.by(sort));
        return adsPeople;
    }
    List<AdsPerson> searchWithCategory(String title, String city, String category, String sort){
        List<AdsPerson> adsPeople = new ArrayList<>();
        if (city.equals("") && title.equals("")){
            adsPeople = adsRepository.findByCategory(category, Sort.by(sort));
        }
        if (!city.equals("")){
            adsPeople = adsRepository.findAllByCategoryAndCity(category, city, Sort.by(sort));
        }
        return adsPeople;
    }
    @RequestMapping(value = "/profile/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity searchAds(@PathVariable long id){
        Optional<AdsPerson> adsPerson = adsRepository.findById(id);
        AuthPerson authPerson = adsPerson.get().getAuthPerson();
        AdsFavorites adsFavorites;
        Long myFavorites = Long.parseLong("0");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() != "anonymousUser"){
            JwtUser userDetails = (JwtUser) authentication.getPrincipal();
            adsFavorites = adsFavoritesRepository.findByAdsPerson_IdAndAuthPerson_Email(id, userDetails.getEmail());
             if (adsFavorites != null) myFavorites = Long.parseLong(adsFavorites.getId().toString());
        }
        return ResponseEntity.ok(new AdsProfileDto(
                adsPerson.get().getId(),
                authPerson.getEmail(),
                authPerson.getProfilePerson().getNumberPhone(),
                adsPerson.get().getTitle(),
                adsPerson.get().getAdsProfile().getDescription(),
                adsPerson.get().getPrice(),
                adsPerson.get().getCity(),
                adsPerson.get().getCategory(),
                adsPerson.get().getRating(),
                adsImageRepository.findByAdsPerson_Id(id),
                myFavorites
        ));
    }
    @RequestMapping(value = "/setrating", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity setRating(@RequestBody AdsReviewsDto adsReviewsdto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtUser userDetails = (JwtUser) authentication.getPrincipal();
        AdsReviews adsReviews = new AdsReviews();
        AuthPerson authPerson = authPersonRepository.findByEmail(userDetails.getEmail());
        Optional<AdsPerson> adsPerson =  adsRepository.findById(adsReviewsdto.getAdsid());
        List<AdsReviews> adsReviews1 = adsReviewRepository.findAllByAdsPerson_Id(adsReviewsdto.getAdsid());
        double commonRating = Double.parseDouble(adsPerson.get().getRating());
        double resultsRating = (commonRating + Double.parseDouble(adsReviewsdto.getRating()))/(adsReviews1.size()+1);
        adsPerson.get().setRating(String.valueOf(resultsRating));
        AdsPerson adsPerson1 = adsPerson.get();
        adsReviews.setAuthPerson(authPerson);
        adsReviews.setRating(adsReviewsdto.getRating());
        adsReviews.setContent(adsReviewsdto.getContent());
        adsReviews.setAdsPerson(adsPerson.get());
        adsReviews.setDate(adsReviewsdto.getDate());
        adsService.save(adsPerson1);
        adsReviewService.save(adsReviews);
        return new ResponseEntity(HttpStatus.CREATED);
    }
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity setRating(@RequestBody AdsRemoveDto adsRemovedto){
        Optional<AdsPerson> adsPerson =  adsRepository.findById(adsRemovedto.getId());
        for (int i = 0; i < adsPerson.get().getAttachments().size(); i++){
            File file = new File(uploadpath + "/" + adsPerson.get().getAttachments().get(i).getFilename());
            file.delete();
        }
        adsService.remove(adsPerson.get());
        return new ResponseEntity(HttpStatus.OK);
    }
    @RequestMapping(value = "/getrating/{id}", method = RequestMethod.GET)
    @ResponseBody
    public AdsReviewDto getRating(@PathVariable long id){
        AdsReviewDto adsReviewDto = new AdsReviewDto();
        adsReviewDto.setAdsReviewList(adsReviewRepository.findAllByAdsPerson_Id(id));
        return adsReviewDto;
    }
    @RequestMapping(value = "/my", method = RequestMethod.GET)
    @ResponseBody
    public List<AdsPerson> getRating(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtUser userDetails = (JwtUser) authentication.getPrincipal();
        List<AdsPerson> adsPersonList = adsRepository.findByAuthPerson_Email(userDetails.getEmail());
        return adsPersonList;
    }

}
