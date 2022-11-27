import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

Future<List<Data>> fetchData() async {
  final response =
  await http.get(Uri.parse('https://jsonplaceholder.typicode.com/posts'));

  List jsonResponse = json.decode(response.body);
  return jsonResponse.map((data) => Data.fromJson(data)).toList();
}

class Data {
  final int userId;
  final int id;
  final String title;
  final String body;

  Data(
      {required this.userId,
        required this.id,
        required this.title,
        required this.body});

  factory Data.fromJson(Map<String, dynamic> json) {
    return Data(
      userId: json['userId'],
      id: json['id'],
      title: json['title'],
      body: json['body'],
    );
  }
}

class SecondPage extends StatefulWidget {
  @override
  _SecondPageState createState() => _SecondPageState();
}

class _SecondPageState extends State<SecondPage> {
  late Future<List<Data>> futureData;

  @override
  void initState() {
    super.initState();
    futureData = fetchData();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        elevation: 1,
        backgroundColor: Colors.white,
        title: Center(
          child: Text(
            'Flutter Tutorial',
            style: TextStyle(
              color: Colors.black,
            ),
          ),
        ),
        automaticallyImplyLeading: false,
      ),
      body: FutureBuilder<List<Data>>(
        future: futureData,
        builder: (context, snapshot) {
          if (snapshot.hasData) {
            List<Data>? data = snapshot.data;
            return ListView.separated(
              itemCount: data!.length,
              itemBuilder: (BuildContext context, index) {
                return Container(
                  height: 240,
                  child: Column(
                    children: [
                      Flexible(
                        flex: 2,
                        child: Row(
                          children: [
                            Flexible(
                                flex: 3,
                                child: Column(
                                  children: [
                                    SizedBox(
                                      height: 10,
                                    ),
                                    Icon(
                                      Icons.circle,
                                      size: 80,
                                      color: Colors.grey,
                                    ),
                                  ],
                                )),
                            Flexible(
                              flex: 7,
                              child: Column(
                                crossAxisAlignment: CrossAxisAlignment.start,
                                children: [
                                  Padding(
                                    padding:
                                    const EdgeInsets.fromLTRB(10, 20, 0, 0),
                                    child: Text(
                                      'userID: ${data[index].userId}',
                                      style: TextStyle(
                                        fontWeight: FontWeight.w900,
                                        fontSize: 17,
                                      ),
                                    ),
                                  ),
                                  Padding(
                                    padding:
                                    const EdgeInsets.fromLTRB(10, 10, 0, 0),
                                    child: Text(
                                      'id: ${data[index].id}',
                                      style: TextStyle(
                                        color: Colors.grey,
                                      ),
                                    ),
                                  ),
                                ],
                              ),
                            ),
                          ],
                        ),
                      ),
                      Flexible(
                          fit: FlexFit.tight,
                          flex: 3,
                          child: Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              Row(
                                children: [
                                  Container(
                                    padding: EdgeInsets.only(left: 6.1),
                                    width: 400,
                                    child: Text(
                                      '${data[index].title}',
                                      style: TextStyle(
                                        fontWeight: FontWeight.w700,
                                        fontSize: 15,
                                      ),
                                    ),
                                  ),
                                ],
                              ),
                              SizedBox(
                                height: 25,
                              ),
                              Row(
                                children: [
                                  Container(
                                    padding: EdgeInsets.only(left: 5),
                                    width: 400,
                                    child: Text(
                                      '${data[index].body}',
                                    ),
                                  ),
                                ],
                              ),
                            ],
                          )),
                    ],
                  ),
                );
              },
              separatorBuilder: (BuildContext context, int index) =>
              const Divider(
                color: Colors.black,
              ),
            );
          } else {
            return Center(child: CircularProgressIndicator());
          }
        },
      ),
    );
  }
}