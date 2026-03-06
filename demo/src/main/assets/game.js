var Canvas = require("canvas");
var glView = __glView;

var canvas = Canvas(glView);
var ctx = canvas.getContext("2d");
var w = canvas.width;
var h = canvas.height;

    var balls = [];
    var numBalls = 12;
    var colors = [
        "#FF6B6B", "#4ECDC4", "#45B7D1", "#96CEB4",
        "#FFEAA7", "#DDA0DD", "#98D8C8", "#F7DC6F",
        "#BB8FCE", "#85C1E9", "#F8C471", "#82E0AA"
    ];

    for (var i = 0; i < numBalls; i++) {
        balls.push({
            x: Math.random() * w,
            y: Math.random() * h,
            r: 15 + Math.random() * 30,
            vx: (Math.random() - 0.5) * 4,
            vy: (Math.random() - 0.5) * 4,
            color: colors[i % colors.length]
        });
    }

var frameCount = 0;

function draw() {
    void canvas;
    frameCount++;

    ctx.fillStyle = "rgba(20, 20, 40, 0.25)";
    ctx.fillRect(0, 0, w, h);

        for (var i = 0; i < balls.length; i++) {
            var b = balls[i];

            b.x += b.vx;
            b.y += b.vy;

            if (b.x - b.r < 0 || b.x + b.r > w) b.vx = -b.vx;
            if (b.y - b.r < 0 || b.y + b.r > h) b.vy = -b.vy;

            b.x = Math.max(b.r, Math.min(w - b.r, b.x));
            b.y = Math.max(b.r, Math.min(h - b.r, b.y));

            ctx.beginPath();
            ctx.arc(b.x, b.y, b.r, 0, Math.PI * 2);
            ctx.fillStyle = b.color;
            ctx.fill();

            ctx.beginPath();
            ctx.arc(b.x - b.r * 0.3, b.y - b.r * 0.3, b.r * 0.3, 0, Math.PI * 2);
            ctx.fillStyle = "rgba(255, 255, 255, 0.3)";
            ctx.fill();
        }

        ctx.fillStyle = "#FFFFFF";
        ctx.font = "16px sans-serif";
        ctx.fillText("Ejecta-V8 Canvas Demo", 10, 30);
        ctx.fillText("Balls: " + numBalls + "  Frame: " + frameCount, 10, 55);


    glView.requestAnimationFrame(draw);
}

glView.requestAnimationFrame(draw);
