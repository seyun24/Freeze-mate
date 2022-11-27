import 'dart:io';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:image_picker/image_picker.dart';
// import 'package:pj_f/storage.dart';
import 'main.dart';
import 'dart:convert';
// import 'dart:io';


String parsedtext = '';

// Future _getFromGallery() async {
//   final pickedFile = await ImagePicker().pickImage(source: ImageSource.gallery);
//   if (pickedFile == null) return;
//
//   var bytes = File(pickedFile.path.toString()).readAsBytesSync();
//   String img64 = base64Encode(bytes);
//
//   var url = 'https://api.ocr.space/parse/image';
//   var payload = {"base64Image": "data:image/jpg;base64,${img64.toString()}","language" :"kor"};
//   var header = {"apikey" :"K89651969488957"};
//
//   var post = await http.post(Uri.parse(url),body: payload,headers: header);
//   var result = jsonDecode(post.body);
//
//   // setState(() {
//     parsedtext = result['ParsedResults'][0]['ParsedText'];
//   // });
// }

class SecondPage extends StatefulWidget {
  @override
  _SecondPageState createState() => _SecondPageState();
}


class _SecondPageState extends State<SecondPage> {
  Future _getFromGallery() async {
    final pickedFile = await ImagePicker().pickImage(source: ImageSource.gallery);
    if (pickedFile == null) return;
    // final Storage storage=Storage();
    // final path=pickedFile.path;
    // storage.uploadFile(path, "d");

    var bytes = File(pickedFile.path.toString()).readAsBytesSync();
    String img64 = base64Encode(bytes);

    var url = 'https://api.ocr.space/parse/image';
    var payload = {"base64Image": "data:image/jpg;base64,${img64.toString()}","language" :"kor"};
    var header = {"apikey" :"K89651969488957"};

    var post = await http.post(Uri.parse(url),body: payload,headers: header);
    var result = jsonDecode(post.body);

    setState(() {
    parsedtext = result['ParsedResults'][0]['ParsedText'];
    });
  }
  @override
  Widget build(BuildContext context) {
    return
      DefaultTabController(
        length: 3,
      child:
      Scaffold(
      appBar: AppBar(
        iconTheme: IconThemeData(color: Colors.grey),
        backgroundColor: Colors.blue,
        elevation: 0.0,
        title: Text("hello"),
        bottom: TabBar(
          tabs: [
            Tab(text: "ocr",),
            Tab(text: "등록",),
            Tab(text: "조회",)
          ],
        ),
      ),
      body:
      TabBarView(children: [
        Container(

          child:
          Column(
          children: [
            Text("${parsedtext}", style:
            TextStyle(color: Colors.black),),
            FlatButton(
              onPressed: () {
                _getFromGallery();
              },
              child: Text("s"),
            )
        ],
      )
        ),
         Container(
           child: GestureDetector(
             onTap: () {
               FocusScope.of(context).unfocus();
             },
             child: SingleChildScrollView(
               child: Column(
                 children: [
                   Padding(
                     padding: const EdgeInsets.fromLTRB(0, 0, 220, 5),
                     child: Text(
                       '상품이름 등록',
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
                                       labelText: '상품등록',
                                       border: OutlineInputBorder(
                                           borderRadius: BorderRadius.circular(4))),
                                   keyboardType: TextInputType.text,
                                   onChanged: (text) {
                                     setState(() {
                                       text3=text;
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
                                           postProducts(text3);
                                           // flutterToast();
                                           // Navigator.push(
                                           //     context,
                                           //     MaterialPageRoute(
                                           //         builder: (BuildContext context) =>
                                           //             MyHomePage()));
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
         ),
      FutureBuilder(
              future: requestEmailAuth(),
              builder: (BuildContext context, AsyncSnapshot snapshot) {
                if (snapshot.hasData) {
                  return GridView.builder(
                      itemCount: snapshot.data.length,//item 개수
                      gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
                        crossAxisCount: 3, //1 개의 행에 보여줄 item 개수
                        childAspectRatio: 2 / 3, //item 의 가로 1, 세로 2 의 비율
                        mainAxisSpacing: 10, //수평 Padding
                        crossAxisSpacing: 10, //수직 Padding
                      ),
                      itemBuilder: (BuildContext context, int index) {
                        return Container(
                          height: 100,
                          child:  Image.network("${snapshot.data[index]["imageLink"]}",height: 100,width:100,fit: BoxFit.fill,)
                        );
                      }

                  );

                } else {
                  return Container();
                }
              } )])
    ));
  }
}