import { useForm } from "react-hook-form";

import { Input } from "@/components/ui/input";
import { zodResolver } from "@hookform/resolvers/zod";
import { Button } from "@/components/ui/button";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormMessage,
} from "@/components/ui/form";
import { loginSchema, LoginSchema } from "../schemas/login-schema";
import { useLogin } from "../hooks/use-login";

export function LoginForm() {
  const { mutate: login, isPending } = useLogin();

  
  const form = useForm<LoginSchema>({
    resolver: zodResolver(loginSchema),
    defaultValues: {
      username: "", 
      password: "",
    },
  });

  function onSubmit(values: LoginSchema) {
    login(values);
  }

  return (
    <Form {...form}>
      <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-8">
        <FormField
          control={form.control}
          name="username" 
          render={({ field }) => (
            <FormItem>
              <FormControl>
                <Input type="text" placeholder="Username" {...field} /> 
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />

        <FormField
          control={form.control}
          name="password"
          render={({ field }) => (
            <FormItem>
              <FormControl>
                <Input type="password" placeholder="Password" {...field} />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />
<div className="bg-red-500 p-10">Test</div>

        <Button type="submit" disabled={isPending}>
          Login
        </Button>
      </form>
    </Form>
  );
}
