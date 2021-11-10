interface Classification {
  classId: number,
  name: string,
  introduction: string,
  color: string,
  createTime: string,
  updateTime: string,
  articleCount: number
}

interface Tag {
  tagId: number,
  name: string,
  color: string,
  createTime: string,
  updateTime: string,
  articleCount: number
}

interface Article {
  articleId: number,
  title: string,
  summary: string,
  htmlContent: string,
  textContent: string,
  articleFace: string,
  type: number,
  author: any,
  words: number,
  comments: number,
  views: number,
  createTime: string,
  updateTime: string,
  classification: Classification,
  tagList: Tag[]
}

// 标签云
interface TagCloud {
  name: string,
  value: number,
}

interface Common {
  fid: number
  fcommentNickname: string // 
  nickname: string, // 发这条评论的游客的昵称
  content: string, // 评论内容
  browser: string, // 所使用的浏览器
  os: string, // 所使用的操作系统
  address: string, // 城市信息
  createTime: string, // 评论时间
  isDelete: boolean
}

// 评论
interface Comment extends Common {
  commentId: number,  
  children: Comment[] // 子评论
}

// 归档页面文章创建频率表对象
interface ArticleDate {
  createTime: string,
  count: number,
}

// 关于我页面
interface About {
  articleCount: number,
  classificationCount: number,
  tagCount: number,
  classifications: Array<Classification>,
  tags: Array<Tag>,
  articleDateVoList: Array<ArticleDate>,
  photo: Array<any>
}

interface Link {
  linkId: number,
  nickName: string,
  introduction: string,
  avatar: string,
  link: string
}

// 留言
interface Message extends Common {
  messageId: number,
  siteUrl: string // 游客的网站信息
  email: string // 游客的邮箱
  children: Message[]
}

interface ArticleSearchResult {
  articleId: number,
  title: string,
  summary: string,
  htmlContent: string
}

// 用户访问量和页面访问量
interface PU {
  pv: number,
  uv: number
}

export {
  Classification,
  Tag,
  Article,
  TagCloud,
  Comment,
  ArticleDate,
  About,
  Link,
  Message,
  ArticleSearchResult,
  PU,  
}