package com.neo.accountapp_3.db;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

public class UseKindsList extends SharedModel {
    String[] UseCategoryTab = {"입금", "지출"}; //고정 값

    String dbname = "";

    public UseKindsList(Context mContext){
        this.oContext = mContext; //부모클래스에 전달

        dbname = "UsekindsCategorey"; //db name 지정

        //고유키 - 커플고유키, 입출금 탭, 카테고리이름
        Field = new String[]{
                "couplekey", "usekindstab", "name"
        };


    }

    //카테고리 입금, 지출 탭 선택
    public String GetCategorytab(int idx){
       return UseCategoryTab[idx];
    }
    //인물 카테고리 객체를 등록한다.
    public String SaveCategory(String Couplekey, String kindstab, String name){
        ArrayList<String> savedata = new ArrayList<>();
        savedata.add(Couplekey); //커플 고유번호
        savedata.add(kindstab); //종류 탭
        savedata.add(name); //이름



        ArrayList<ArrayList<ArrayList<String>>> alllist = DoReadAll(dbname);

        int maxkey = 0;
        //값이 아무것도 없을때 처리를 해줘야돼.
        if(alllist.isEmpty()) {
            maxkey = alllist.size();
        }else{
            //키값을 만들어야한다. 전체 리스트를 가져와서 가장 큰 숫자를 찾고 +1을 한다.
            ArrayList<Integer> sortdata = new ArrayList<>();
            for (int i = 0; i < alllist.size(); i++){
                String eachkey = alllist.get(i).get(0).get(0);
                sortdata.add(Integer.parseInt(eachkey));
            }
            maxkey = Collections.max(sortdata)+1; //키값으로 쓰임
        }


        //Log.d("alllist list sort", String.valueOf(sortdata));
        //Log.d("alllist list sort2", String.valueOf(maxkey));

        String key = String.valueOf(maxkey);
        String getkey = DoAdd_json(dbname, key, savedata); //저장하고 그 key값을 던져줌

        //Log.d("저장할 값들", String.valueOf(accountdata));
        Log.d("SaveWhoCategory", getkey+"저장완료");
        return getkey;
    }

    //사용 카테고리 기본 데이터를 저장한다.
    public int SaveBasicCategory(String Couplekey){
        if(!Couplekey.equals("")) {
            //입금 - 월급 보너스 적금 용돈 이월 기타
            //지출 - 식비 교통 문화 오락 교통 여행 패션 미용 생필품 통신 주거비 대출이자 공과금 편의점 건강 카페 담배 술 취미 회비 회사 용돈 선물 데이트 복권 기타

            //기본으로 넣을 데이터 array로 만듬 : 사용 카테고리 명칭들
            String[] BasicInputArr = {"월급", "보너스", "적금", "용돈", "이월", "기타"};
            String[] BasicOutputArr = {"식비", "교통", "문화", "오락", "여행", "패션", "미용", "생필품", "통신", "주거비", "대출이자", "공과금", "편의점", "건강", "카페", "담배", "술", "취미", "회사", "용돈", "선물", "데이트", "복권", "기타"};

            //입금 리스트
            for (int i = 0; i < BasicInputArr.length; i++){
                String DataAlias = BasicInputArr[i];

                ArrayList<String> savedata = new ArrayList<>();
                savedata.clear();//초기화
                savedata.add(Couplekey); //커플 고유번호
                savedata.add("0"); //사용 카테고리 탭
                savedata.add(DataAlias); //데이터 이름

                ArrayList<ArrayList<ArrayList<String>>> alllist = DoReadAll(dbname);

                int maxkey = 0;
                //값이 아무것도 없을때 처리를 해줘야돼.
                if(alllist.isEmpty()) {
                    maxkey = alllist.size();
                }else{
                    //키값을 만들어야한다. 전체 리스트를 가져와서 가장 큰 숫자를 찾고 +1을 한다.
                    ArrayList<Integer> sortdata = new ArrayList<>();
                    for (int j = 0; j < alllist.size(); j++){
                        String eachkey = alllist.get(j).get(0).get(0);
                        sortdata.add(Integer.parseInt(eachkey));
                    }
                    maxkey = Collections.max(sortdata)+1; //키값으로 쓰임
                }

                String key = String.valueOf(maxkey);



                String getkey = DoAdd_json(dbname, key, savedata); //저장하고 그 key값을 던져줌

                Log.d("Save 입금 Category", getkey + "저장완료");
            }

            //지출 리스트
            for (int i = 0; i < BasicOutputArr.length; i++){
                String DataAlias = BasicOutputArr[i];

                ArrayList<String> savedata = new ArrayList<>();
                savedata.clear();//초기화
                savedata.add(Couplekey); //커플 고유번호
                savedata.add("1"); //사용 카테고리 탭
                savedata.add(DataAlias); //데이터 이름

                ArrayList<ArrayList<ArrayList<String>>> alllist = DoReadAll(dbname);

                int maxkey = 0;
                //값이 아무것도 없을때 처리를 해줘야돼.
                if(alllist.isEmpty()) {
                    maxkey = alllist.size();
                }else{
                    //키값을 만들어야한다. 전체 리스트를 가져와서 가장 큰 숫자를 찾고 +1을 한다.
                    ArrayList<Integer> sortdata = new ArrayList<>();
                    for (int j = 0; j < alllist.size(); j++){
                        String eachkey = alllist.get(j).get(0).get(0);
                        sortdata.add(Integer.parseInt(eachkey));
                    }
                    maxkey = Collections.max(sortdata)+1; //키값으로 쓰임
                }

                String key = String.valueOf(maxkey);

                String getkey = DoAdd_json(dbname, key, savedata); //저장하고 그 key값을 던져줌

                Log.d("Save 지출 Category", getkey + "저장완료");
            }

            return 1;
        }else{
            return 0;
        }
    }


    //데이터 전부 가져오기
    public ArrayList<ArrayList<ArrayList<String>>> AlllistRead(){

        ArrayList<ArrayList<ArrayList<String>>> Accountlist = DoReadAll(dbname);

        return Accountlist;
    }
    //데이터 하나 가져오기
    public  ArrayList<ArrayList<String>> Getoneinfo(String key){

        // result = DoReadOne(dbname, key);
        ArrayList<ArrayList<String>> result = DoReadOne(dbname, key);
        return result;
    }

    //db초기화
    public int Init(){
        //User라는 데이터베이스에 값을 추가할 것. Userdb가 없으면 만들고 putString을 하고, 있으면 찾아서 putString 할것
        //찾을 db 이름, 저장할 데이터들,

        int Accountlist = DoDeleteAll(dbname);

        return Accountlist;
    }

    //하나만 삭제
    public int Deleteone(String key){
        String[] keyarr = {key};
        DoDeleteArray(dbname, keyarr);

        return 1;
    }

    //해당 데이터를 수정한다.
    public String EditCategory(String idxkey, String Couplekey, String usekindstab, String name){
        Log.d("커플 키", Couplekey); //데이터 변경 전값을 가져와야함
        Log.d("인물 카테고리 키", String.valueOf(idxkey)); //데이터 변경 전값을 가져와야함

        ArrayList<String> changedata = new ArrayList<>();
        changedata.add(Couplekey); //커플 고유번호
        changedata.add(usekindstab); //사용 카테고리 탭
        changedata.add(name); //사용 카테고리 이름

        //키값이 이미 존재하면 덮어 씌움
        String key = idxkey; //키는 리스트에서 가장 큰 숫자
        String GetWhokey = DoAdd_json(dbname, key, changedata); //저장하고 그 key값을 던져줌

        //Log.d("저장할 값들", String.valueOf(accountdata));
        Log.d("TAG", GetWhokey+"수정완료");


        return GetWhokey;
    }
}
