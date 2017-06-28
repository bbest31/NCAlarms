namespace NapChatService.Migrations
{
    using DataObjects;
    using Microsoft.Azure.Mobile.Server.Tables;
    using System;
    using System.Collections.Generic;
    using System.Data.Entity;
    using System.Data.Entity.Migrations;
    using System.Linq;

    internal sealed class Configuration : DbMigrationsConfiguration<NapChatService.Models.NapChatContext>
    {
        public Configuration()
        {
            AutomaticMigrationsEnabled = false;
            SetSqlGenerator("System.Data.SqlClient", new EntityTableSqlGenerator());
        }

        protected override void Seed(NapChatService.Models.NapChatContext context)
        {
            //  This method will be called after migrating to the latest version.
            List<User> Users = new List<User>
            {
                new User {Id = Guid.NewGuid().ToString(), Username="Username" },
                new User {Id = Guid.NewGuid().ToString(), Username = "AnotherUser" }
            };

            foreach (User user in Users)
            {
                context.Set<User>().Add(user);
            }
            base.Seed(context);
            //  You can use the DbSet<T>.AddOrUpdate() helper extension method 
            //  to avoid creating duplicate seed data. E.g.
            //
            //    context.People.AddOrUpdate(
            //      p => p.FullName,
            //      new Person { FullName = "Andrew Peters" },
            //      new Person { FullName = "Brice Lambson" },
            //      new Person { FullName = "Rowan Miller" }
            //    );
            //
        }
    }
}
