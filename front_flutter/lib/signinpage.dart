import 'package:flutter/material.dart';

import 'main.dart';
import 'name.dart';

class SignInPage extends StatefulWidget {
  @override
  _SignInPageState createState() => _SignInPageState();
}

class _SignInPageState extends State<SignInPage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        iconTheme: IconThemeData(color: Colors.grey),
        backgroundColor: Colors.white10,
        elevation: 0.0,
      ),
      body: GestureDetector(
        onTap: () {
          FocusScope.of(context).unfocus();
        },
        child: SingleChildScrollView(
          child: Column(
            children: [
              Padding(
                padding: const EdgeInsets.fromLTRB(0, 0, 120, 0),
                child: Text(
                  '아이디로 회원가입',
                  style: TextStyle(fontSize: 30, fontWeight: FontWeight.w600),
                ),
              ),
              Form(
                  child: Theme(
                    data: ThemeData(
                        primarySwatch: Colors.amber,
                        inputDecorationTheme: InputDecorationTheme(
                          labelStyle: TextStyle(color: Colors.grey, fontSize: 15.0),
                        )),
                    child: Container(
                      padding: EdgeInsets.fromLTRB(30, 10, 30, 30),
                      child: Column(
                        children: [
                          Padding(
                            padding: const EdgeInsets.fromLTRB(0, 0, 100, 0),
                            child: Text(
                              '로그인은 최소 5자 이상으로 입력해주세요',
                              style: TextStyle(
                                color: Colors.grey,
                              ),
                            ),
                          ),
                          SizedBox(
                            height: 30,
                          ),
                          TextField(
                            controller: controller3,
                            decoration: InputDecoration(
                                suffixIcon: IconButton(
                                  onPressed: () => controller3.clear(),
                                  color: Colors.grey,
                                  icon: Icon(Icons.clear),
                                ),
                                labelText: '아이디 입력',
                                border: OutlineInputBorder(
                                    borderRadius: BorderRadius.circular(4))),
                            keyboardType: TextInputType.text,
                            onChanged: (text) {
                              setState(() {
                                if (text.length >= 5) {
                                  text1 = controller3.text;
                                  x = true;
                                } else {
                                  x = false;
                                }
                              });
                            },
                          ),
                          SizedBox(
                            height: 20,
                          ),
                          TextField(
                            controller: controller4,
                            decoration: InputDecoration(
                                suffixIcon: IconButton(
                                  onPressed: () => controller4.clear(),
                                  color: Colors.grey,
                                  icon: Icon(Icons.clear),
                                ),
                                labelText: '비밀번호 입력',
                                border: OutlineInputBorder(
                                    borderRadius: BorderRadius.circular(4))),
                            keyboardType: TextInputType.text,
                            obscureText: true,
                          ),
                          SizedBox(
                            height: 20,
                          ),
                          TextField(
                            controller: controller5,
                            decoration: InputDecoration(
                                suffixIcon: IconButton(
                                  onPressed: () => controller5.clear(),
                                  color: Colors.grey,
                                  icon: Icon(Icons.clear),
                                ),
                                labelText: '비밀번호 확인',
                                border: OutlineInputBorder(
                                    borderRadius: BorderRadius.circular(4))),
                            keyboardType: TextInputType.text,
                            obscureText: true,
                            onChanged: (text) {
                              setState(() {
                                if (controller4.text == controller5.text) {
                                  y = true;
                                  text2 = controller4.text;
                                } else {
                                  y = false;
                                }
                              });
                            },
                          ),
                          SizedBox(
                            height: 40,
                          ),
                          ButtonTheme(
                              minWidth: 100,
                              height: 50,
                              child: FlatButton(
                                  color: x == true && y == true
                                      ? Colors.amber
                                      : Colors.white10,
                                  onPressed: () {
                                    postFetch(text1, text2);
                                    if (x == true && y == true) {
                                      Navigator.push(
                                          context,
                                          MaterialPageRoute(
                                              builder: (BuildContext context) =>
                                                  NamePage()));
                                    }
                                  },
                                  child: Row(
                                    mainAxisAlignment: MainAxisAlignment.center,
                                    children: [
                                      Text(
                                        '다음으로',
                                        style: TextStyle(
                                            color: x == true && y == true
                                                ? Colors.white
                                                : Colors.grey),
                                      )
                                    ],
                                  ))),
                        ],
                      ),
                    ),
                  )),
            ],
          ),
        ),
      ), //2
    );
  }
}