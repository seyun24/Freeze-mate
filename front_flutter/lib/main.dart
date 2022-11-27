import 'dart:async';
import 'dart:convert';

import 'package:http/http.dart' as http;
import 'package:flutter/material.dart';
import 'package:image_picker/image_picker.dart';
import 'package:pj_f/second.dart';
import 'package:pj_f/signinpage.dart';


import 'name.dart';

// bool ad = false;


bool a = false;
bool b = false;
bool x = false;
bool y = false;
bool z = false;

String? text1;
String? text2;
String? text3;

TextEditingController controller = TextEditingController();
TextEditingController controller2 = TextEditingController();

TextEditingController controller3 = TextEditingController();
TextEditingController controller4 = TextEditingController();
TextEditingController controller5 = TextEditingController();

TextEditingController controller6 = TextEditingController();


void postFetch(String? userName, String? password) async {
  // Map<String, String> body2 = {};
  // String userName="park";
  // String password="192837";
  var data = {
    "userName" : userName,
    "password" : password,
  };

  final response = await http.post(
    Uri.parse('http://10.0.2.2:9000/app/users/post/laded'),
    headers: <String, String>{
      'Content-Type': 'application/json; charset=UTF-8',
    },
    body: jsonEncode(data),
  );


  print("5");

  var body = json.encode(data);
  // http.Response _res = await http.post("$response}/data",
  //     headers: {"Content-Type": "application/json"},
  //     body: body
  // );
}

void postProducts(String? productsName) async {
  // Map<String, String> body2 = {};
  // String userName="park";
  // String password="192837";
  // String imageLink=Null as String;

  var data = {
    "productsName" : productsName
    // "imageLink" : imageLink,
  };

  final response = await http.post(
    Uri.parse('http://10.0.2.2:9000/app/users/products/post/pod'),
    headers: <String, String>{
      'Content-Type': 'application/json; charset=UTF-8',
    },
    body: jsonEncode(data),
  );


  print("5");

  var body = json.encode(data);
  // http.Response _res = await http.post("$response}/data",
  //     headers: {"Content-Type": "application/json"},
  //     body: body
  // );
}


Future<List> requestEmailAuth() async {
  List ad;
  final response = await http.get(Uri.parse('http://10.0.2.2:9000/app/users/test/log'));
  ad = jsonDecode(response.body)["result"];
  print(ad);
  return ad;
}

Future<bool> postLogin(String? userName, String? password) async {
  // Map<String, String> body2 = {};
  // String userName="park";
  // String password="192837";
  var data = {
    "userName" : userName,
    "password" : password,
  };

  final response = await http.post(
    Uri.parse('http://10.0.2.2:9000/app/users/log-in'),
    headers: <String, String>{
      'Content-Type': 'application/json; charset=UTF-8',
    },
    body: jsonEncode(data),
  );

  bool ad = jsonDecode(response.body)["isSuccess"];
  print(ad);

  return ad;
  var body = json.encode(data);
  // http.Response _res = await http.post("$response}/data",
  //     headers: {"Content-Type": "application/json"},
  //     body: body
  // );
}


void main() {
  // WidgetsFlutterBinding.ensureInitialized();
  // await Firebase.initializeApp();
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      home: MyHomePage(),
    );
  }
}

class MyHomePage extends StatefulWidget {
  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {

  @override
  Widget build(BuildContext context) {
    return  Scaffold(
      body: GestureDetector(
        onTap: () {
          FocusScope.of(context).unfocus();
        },
        child: SingleChildScrollView(
          child: Column(
            children: [
              SizedBox(
                height: 50,
              ),
              Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Text(
                    '로그인',
                    style: TextStyle(
                      fontSize: 30,
                      color: Colors.black,
                    ),
                  ),
                ],
              ),
              SizedBox(
                height: 10,
              ),
              Text(
                '로그인하신 후 모든 서비스를 이용하실수 있습니다',
                style: TextStyle(
                  fontSize: 13,
                  color: Colors.grey,
                ),
              ),
              Form(
                  child: Theme(
                      data: ThemeData(
                          primarySwatch: Colors.amber,
                          inputDecorationTheme: InputDecorationTheme(
                              labelStyle: TextStyle(
                                  color: Colors.grey, fontSize: 15.0))),
                      child: Container(
                        padding: EdgeInsets.all(30),
                        child: Column(
                          children: [
                            TextField(
                              controller: controller,
                              decoration: InputDecoration(
                                labelText: '아이디 입력',
                                suffixIcon: IconButton(
                                  onPressed: () => controller.clear(),
                                  color: Colors.grey,
                                  icon: Icon(Icons.cancel),
                                ),
                                border: OutlineInputBorder(
                                    borderRadius: BorderRadius.circular(4)),
                              ),
                              keyboardType: TextInputType.text,
                              onChanged: (text) {
                                setState(() {
                                  if (text.length >= 5) {
                                    a = true;
                                    text1=text;
                                  } else {
                                    a = false;
                                  }

                                });
                              },
                            ),
                            SizedBox(
                              height: 30,
                            ),
                            TextField(
                              controller: controller2,
                              decoration: InputDecoration(
                                  labelText: '비밀번호 입력',
                                  suffixIcon: IconButton(
                                    onPressed: () => controller2.clear(),
                                    color: Colors.grey,
                                    icon: Icon(Icons.cancel),
                                  ),
                                  border: OutlineInputBorder(
                                      borderRadius: BorderRadius.circular(4))),
                              keyboardType: TextInputType.text,
                              obscureText: true,
                              onChanged: (text) {
                                setState(() {
                                  if (text.length >= 5) {
                                    b = true;
                                    text2=text;


                                  } else {
                                    b = false;
                                  }
                                });
                              },
                            ),
                            SizedBox(
                              height: 30,
                            ),
                            ButtonTheme(
                                minWidth: 100,
                                height: 50,
                                child: FlatButton(
                                  color: a && b ? Colors.amber : Colors.white10,
                                  onPressed: () async {

                                    // if (controller.text == text1 &&
                                    //     controller2.text == text2) {


                                          if(await postLogin(text1, text2))
                                          {
                                            Navigator.push(
                                                context,
                                                MaterialPageRoute(
                                                    builder: (BuildContext context) =>
                                                        SecondPage()));
                                          }
                                  },
                                  child: Row(
                                    mainAxisAlignment: MainAxisAlignment.center,
                                    children: [
                                      Text(
                                        '로그인',
                                        style: TextStyle(
                                          fontSize: 18,
                                          color: a && b
                                              ? Colors.white
                                              : Colors.grey,
                                        ),
                                      ),
                                    ],
                                  ),
                                )),
                            SizedBox(
                              height: 10,
                            ),
                            Row(
                              mainAxisAlignment: MainAxisAlignment.center,
                              children: [
                                ButtonTheme(
                                  child: FlatButton(
                                    onPressed: () {
                                      Navigator.push(
                                          context,
                                          MaterialPageRoute(
                                              builder: (BuildContext context) =>
                                                  SignInPage()));
                                    },
                                    child: Text(
                                      '회원가입',
                                      style: TextStyle(
                                        color: Colors.grey,
                                      ),
                                    ),
                                  ),
                                ),
                                Text(
                                  '|',
                                  style: TextStyle(
                                    color: Colors.grey,
                                  ),
                                ),
                                SizedBox(
                                  width: 15,
                                ),
                                Text(
                                  '비밀번호 찾기',
                                  style: TextStyle(
                                    color: Colors.grey,
                                  ),
                                ),
                              ],
                            )
                          ],
                        ),
                      )))
            ],
          ),
        ),
      ),
    );
  }
}
