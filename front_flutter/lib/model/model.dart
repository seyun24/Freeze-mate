import 'dart:convert';
import 'package:http/http.dart' as http;

class Info {
  final int userId;
  final String userName;
  final String password;

  Info(
      {required this.userId,
        required this.userName,
        required this.password,
      });

  factory Info.fromJson(Map<String, dynamic> json) {
    return Info(
        userId: json["userId"],
        userName: json["userName"],
        password: json["password"]
    );
  }
}


class PostViewModel {

  var url =  'https://seyun298.site/app/users/test/log';
  Future<List<Info>> fetchPost() async {



    final response = await http.get(Uri.parse(url));




    if (response.statusCode == 200) {



      // 만약 서버가 OK 응답을 반환하면, JSON을 파싱합니다.



      List responseJson = json.decode(response.body);



      return responseJson.map((post) => new Info.fromJson(post)).toList();



    } else {



      // 만약 응답이 OK가 아니면, 에러를 던집니다.



      throw Exception('Failed to load post');



    }



  }



}
