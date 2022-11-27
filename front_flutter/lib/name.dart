import 'package:flutter/material.dart';
// import 'package:fluttertoast/fluttertoast.dart';



import 'main.dart';

class NamePage extends StatefulWidget {
  @override
  _NamePageState createState() => _NamePageState();
}

class _NamePageState extends State<NamePage> {
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
                padding: const EdgeInsets.fromLTRB(0, 0, 220, 5),
                child: Text(
                  '닉네임 입력',
                  style: TextStyle(fontSize: 26, fontWeight: FontWeight.w600),
                ),
              ),
              Form(
                  child: Theme(
                      data: ThemeData(
                          primarySwatch: Colors.amber,
                          inputDecorationTheme: InputDecorationTheme(
                            labelStyle:
                            TextStyle(color: Colors.grey, fontSize: 15.0),
                          )),
                      child: Container(
                        padding: EdgeInsets.all(30),
                        child: Column(
                          children: [
                            TextField(
                              controller: controller6,
                              decoration: InputDecoration(
                                  suffixIcon: IconButton(
                                    onPressed: () => controller6.clear(),
                                    color: Colors.grey,
                                    icon: Icon(Icons.clear),
                                  ),
                                  labelText: '닉네임 입력',
                                  border: OutlineInputBorder(
                                      borderRadius: BorderRadius.circular(4))),
                              keyboardType: TextInputType.text,
                              onChanged: (text) {
                                setState(() {

                                  if (text.isEmpty) {
                                    z = false;
                                  } else {
                                    z = true;
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
                                  color:
                                  z == true ? Colors.amber : Colors.white10,
                                  onPressed: () {
                                    if (z == true) {
                                      // flutterToast();
                                      Navigator.push(
                                          context,
                                          MaterialPageRoute(
                                              builder: (BuildContext context) =>
                                                  MyHomePage()));
                                    }
                                  },
                                  child: Row(
                                    mainAxisAlignment: MainAxisAlignment.center,
                                    children: [
                                      Text(
                                        '입력',
                                        style: TextStyle(
                                            color: z == true
                                                ? Colors.white
                                                : Colors.grey),
                                      ),
                                    ],
                                  )),
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

// void flutterToast() {
//   Fluttertoast.showToast(
//     msg: "회원가입이 완료되었습니다.",
//     gravity: ToastGravity.CENTER,
//     backgroundColor: Colors.black,
//     fontSize: 15,
//     textColor: Colors.white,
//   );
// }