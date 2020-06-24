using OpenTK;
using OpenTK.Graphics;

namespace CSharpRaycaster
{
    class Program
    {
        private static int WindowWidth = 1024, WindowHeight = 512;
        private static string Title = "Raycaster";
        static void Main(string[] args)
        {

            Game game = new Game(WindowWidth, WindowHeight, Title);
            game.main();
        }
    }
}
