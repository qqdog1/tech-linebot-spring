![](https://github.com/qqdog1/tech-linebot-spring/workflows/Java%20CI/badge.svg)

# tech-linebot-spring

輸入help 取得所有command及說明  

---------
## APIs  
### POST /broadcast  
對全部人進行廣播  
FormBody:  
<table>
<tr><td>text</td><td>要廣播的訊息</td></tr>
</table>  

### POST /data/update  
更新cache  
FormBody:  
<table>  
<tr><td>data</td><td>要更新的內容(json format)</td></tr>
</table>  

  {
    "description": "每日什麼什麼",
    "lastUpdate": "2020/02/28",
    "command": "bestsell",
    "value": "1. 1101台 尼 \n 2. 2330台機電\n"
  }

### DELETE /data/update  
移除特定cache  
FormBody:  
<table>
<tr><td>command</td><td>要移除的command</td></tr>
</table>  
