### JSON2Sql

自己写的一个小工具, 将特定的json转换为sql 中的条件

输入:

```json
{
    "and": {
        "or": {
            "and": {
                "eq": {
                    "A.carNo": "苏A8888",
                    "B.carNo": "苏A9999"
                },
                "lt": {
                    "B.id": 100
                }
            },
            "eq": {
                "A.carNo": "苏A8888"
            },
            "lt": {
                "B.id": 100
            }
        },
        "eq": {
            "C.name": "hello"
        },
        "between": {
            "A.type": ["X", "Y"],
            "B.date": ["2019-09-09", "2020-12-12"]
        },
        "like": {
            "A.type": "%sad%"
        },
        "in": {
            "A.id": ["1", "2", "3"],
            "B.id": ["4", "5", "6", "7"]
        }
    }
}
```



输出：

```sql
((B.date BETWEEN '2019-09-09' AND '2020-12-12') and (A.type BETWEEN 'X' AND 'Y')) and (C.name='hello') and (A.id in ('1','2','3') and B.id in ('4','5','6','7')) and (A.type like '%sad%') and (((B.carNo='苏A9999' and A.carNo='苏A8888') and (B.id<'100')) or (A.carNo='苏A8888') or (B.id<'100'))
```

