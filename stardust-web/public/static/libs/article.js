$(function () {
  // Prism.highlightAllUnder(document.getElementById("articleContent"));

  // 加上三个小点
  $("pre").wrap('<div class="code-area" style="position: relative"></div>');

  tocbot.init({
    tocSelector: "#toc-content",
    contentSelector: "#articleContent",
    headingsOffset: -($(window).height() * 0.4 - 45),
    collapseDepth: Number("0"),
    headingSelector: "h2, h3, h4",
  });

  // modify the toc link href to support Chinese.
  let i = 0;
  let tocHeading = "toc-heading-";
  $("#toc-content a").each(function () {
    $(this).attr("href", "#" + tocHeading + ++i);
    // $(this).attr('href', 'javascript:void(0)');
    // $(this).attr('@click', `goAnchor(#${tocHeading + (++i)})`);
    // $(this).attr('onclick', toAnchor('123'));
  });

  // modify the heading title id to support Chinese.
  i = 0;
  $("#articleContent")
    .children("h2, h3, h4")
    .each(function () {
      $(this).attr("id", tocHeading + ++i);
    });

  // Set scroll toc fixed.
  let tocHeight = parseInt($(window).height() * 0.4 - 64);
  let $tocWidget = $(".toc-widget");
  $(window).scroll(function () {
    let scroll = $(window).scrollTop();
    /* add post toc fixed. */
    if (scroll > tocHeight) {
      $tocWidget.addClass("toc-fixed");
    } else {
      $tocWidget.removeClass("toc-fixed");
    }
  });

  /* 修复文章卡片 div 的宽度. */
  let fixPostCardWidth = function (srcId, targetId) {
    let srcDiv = $("#" + srcId);
    if (srcDiv.length === 0) {
      return;
    }

    let w = srcDiv.width();
    if (w >= 450) {
      w = w + 21;
    } else if (w >= 350 && w < 450) {
      w = w + 18;
    } else if (w >= 300 && w < 350) {
      w = w + 16;
    } else {
      w = w + 14;
    }
    $("#" + targetId).width(w);
  };

  // 切换TOC目录展开收缩的相关操作.
  const expandedClass = "expanded";
  let $tocAside = $("#toc-aside");
  let $mainContent = $("#main-content");
  $("#floating-toc-btn .btn-floating").click(function () {
    if ($tocAside.hasClass(expandedClass)) {
      $tocAside.removeClass(expandedClass).hide();
      $mainContent.removeClass("l9");
    } else {
      $tocAside.addClass(expandedClass).show();
      $mainContent.addClass("l9");
    }
    fixPostCardWidth("artDetail", "prenext-posts");
  });
});
